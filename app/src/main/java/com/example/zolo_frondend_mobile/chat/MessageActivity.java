package com.example.zolo_frondend_mobile.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.group.Group;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    ImageButton btnImgSend;
    ImageView imgMess;
    TextView tvNameMess;
    EditText textsend;
    Integer lastId = 0;
    Integer minMessId = 0;
    Boolean isScroll = false;

    MessageAdapter adt;
    public List<MessageGet> mChats = new ArrayList<>();
    RecyclerView recycler_vew;

    Group mGroup;
    Intent mIntent;

//    private String URL_SERVER = "https://zolo-backend.herokuapp.com/";
    private String URL_SERVER = "http://192.168.100.4:3000/";
    private Socket mSocket;
    {
        try{
            mSocket = IO.socket(URL_SERVER);
        }catch (URISyntaxException e){
            Log.v("Hoang", "error connecting to socket");
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);



        btnImgSend = findViewById(R.id.btn_send);
        textsend = findViewById(R.id.textsend);
        recycler_vew = findViewById(R.id.recycler_vew);
        imgMess = findViewById(R.id.imgMessa);
        tvNameMess = findViewById(R.id.tvNameMess);
//================
        Log.v("Hoang", "try to connect");
        mSocket.connect();
        Log.v("Hoang", "connection sucessful");
        mSocket.on("chat message",onNewMessage);
//===============
//        adt = new MessageAdapter(mChats);
//        recycler_vew.setAdapter(adt);
        recycler_vew.setHasFixedSize(true);
        adt = new MessageAdapter(mChats);
        recycler_vew.setAdapter(adt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recycler_vew.setLayoutManager(linearLayoutManager);

        mGroup = (Group) getIntent().getSerializableExtra("group");

        tvNameMess.setText(mGroup.getName());
        imgMess.setImageResource(R.drawable.tuong);

        if(getIntent().getSerializableExtra("friend") != null){
            Friend friend = (Friend) getIntent().getSerializableExtra("friend");
            tvNameMess.setText(friend.getFullName());
        }

        readMessage();

        btnImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textsend.getText().toString();
                if(msg.trim().equals("")){
                    Toast.makeText(MessageActivity.this, "Type message", Toast.LENGTH_SHORT).show();
                }else{
                    //luu du lieu, tai lai du lieu

                    MessageSend a = new MessageSend();
                    a.setMess(textsend.getText().toString().trim());
                    a.setChatgroupId(mGroup.getId());
                    a.setUserId(JWTUtils.USER_ZOLO.getId());
                    Date da = new Date();
                    a.setCreateAt(da.getTime()); //doi nga gio java
//                    Date date = new Date(valueFromClient);
//
//                    String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    Gson gson = new Gson();
                    String json = gson.toJson(a);
                    SaveMess(mGroup, textsend);
//                    recycler_vew.scrollToPosition(mChats.size()-1);
                    mSocket.emit("chat message", json);
                }
                textsend.setText("");

            }
        });

        recycler_vew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(isScroll){
                    getMinId();
                    Log.e("TAG", "onScrollStateChanged: " + minMessId );
                    if(!recycler_vew.canScrollVertically(-1)){
                        if(mChats.get(0).getId() != minMessId){
                            readMessage();
                            recycler_vew.scrollToPosition(mChats.size()-1);
                        }
                }
//                    Toast.makeText(MessageActivity.this, "last", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set lai img va ten group hoac ban be


    }

    private void SaveMess(Group group, EditText textsend) {
        ApiHeaderService.apiService.createChat(group.getId(),textsend.getText().toString().trim()).enqueue(new Callback<StatusChat>() {
            @Override
            public void onResponse(Call<StatusChat> call, Response<StatusChat> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        Toast.makeText(MessageActivity.this, "luu chat thanh cong", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MessageActivity.this, "Get chat group fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusChat> call, Throwable t) {

            }
        });
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0]; //{"chatgroupId":57,"content":"2","createAt":123456,"userId":356}
//                    Log.v("Hoang",data);
                    try {

                        JSONObject obj = new JSONObject(data);
                        String content = obj.getString("mess");
                        Integer groupId = obj.getInt("chatgroupId");
                        Integer userId = obj.getInt("userId");
                        Long createAt = obj.getLong("createAt");
                        MessageGet messageGet = new MessageGet();
                        messageGet.setContent(content);
                        messageGet.setChatgroupId(groupId);
                        messageGet.setUserId(userId);
                        messageGet.setCreateAt(createAt);
                        if(messageGet.getChatgroupId() == mGroup.getId()){
                            mChats.add(messageGet);
                            adt.changList(mChats);
//                            if(isScroll){
                                recycler_vew.scrollToPosition(mChats.size()-1);
//                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void readMessage(){

        if(lastId == 0){
            getLastId();
        }else{
            if(mChats.size() != 0 ){
                lastId = mChats.get(0).getId();
            }
        }
        Log.e("TAG", "readMessage: "+lastId );
        ApiHeaderService.apiService.getAllMessage(mGroup.getId(),10,lastId).enqueue(new Callback<StatusMessageGet>() {
            @Override
            public void onResponse(Call<StatusMessageGet> call, Response<StatusMessageGet> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        if(response.body().getData().size() != 0){
                            isScroll = true;
                            //                        List<MessageGet> messageGets = new ArrayList<>();
                            List<MessageGet> chats = new ArrayList<>();
                            chats = response.body().getData();
//                        mChats = response.body().getData();
                            for (MessageGet chat: mChats) {
                                chats.add(chat);
                            }
                            mChats = new ArrayList<>();
                            mChats = chats;
//                        Toast.makeText(MessageActivity.this, "Get chat group success "+mChats, Toast.LENGTH_SHORT).show();
//                        Log.d("Hoang", "onResponse: "+mChats);
//                        Log.d("Hoang1", "onResponse: "+ JWTUtils.USER_ZOLO.getId());
//                        sortDESC(messageGets);
                            //giam dan
                            Collections.sort(mChats, new Comparator<MessageGet>() {
                                @Override
                                public int compare(MessageGet o1, MessageGet o2) {
                                    return o1.getCreateAt().compareTo(o2.getCreateAt());
                                }
                            });

                            adt = new MessageAdapter(mChats);
                            recycler_vew.setAdapter(adt);
                            adt.changList(mChats);
//                        recycler_vew.scrollToPosition(mChats.size()-1);
                        }

                    }else{
                        Toast.makeText(MessageActivity.this, "Get chat group fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusMessageGet> call, Throwable t) {

            }
        });
//        mChats.add(mess);

//        adt = new MessageAdapter(mChats,MessageAdapter.this, imgeurl);
//        recycler_vew.setAdapter(adt);
    }

    public static void sortDESC(List<MessageGet> arr) {
        MessageGet temp = arr.get(0);
        for (int i = 0 ; i < arr.size() - 1; i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i).getCreateAt() > arr.get(j).getCreateAt()) {
                    swap(arr.get(j), arr.get(i));
                }
            }
        }


    }
    private static void swap(MessageGet a, MessageGet b){
        MessageGet temp = a;
        a = b;
        b = temp;
    }

    private void getLastId(){

        ApiHeaderService.apiService.getLastMessId(mGroup.getId()).enqueue(new Callback<StatusMessageGet>() {
            @Override
            public void onResponse(Call<StatusMessageGet> call, Response<StatusMessageGet> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        if(response.body().getData().size() == 0){

                        }else{
                            Toast.makeText(MessageActivity.this, "getLastId thanh cong", Toast.LENGTH_SHORT).show();
                            lastId = response.body().getData().get(0).getId();
                        }
                    }else{
                        Toast.makeText(MessageActivity.this, "getLastId  fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){

                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusMessageGet> call, Throwable t) {

            }
        });
    }
    private void getMinId(){

        ApiHeaderService.apiService.getMinMessId(mGroup.getId()).enqueue(new Callback<StatusMessageGet>() {
            @Override
            public void onResponse(Call<StatusMessageGet> call, Response<StatusMessageGet> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        if(response.body().getData().size() != 0){
                            Toast.makeText(MessageActivity.this, "get min thanh cong " + minMessId, Toast.LENGTH_SHORT).show();
                            minMessId = response.body().getData().get(0).getId();
                        }
                    }else{
                        Toast.makeText(MessageActivity.this, "get min  fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(MessageActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(MessageActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusMessageGet> call, Throwable t) {

            }
        });
    }
}