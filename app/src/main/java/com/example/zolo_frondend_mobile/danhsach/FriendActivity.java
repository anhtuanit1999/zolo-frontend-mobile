package com.example.zolo_frondend_mobile.danhsach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.MainActivity;
import com.example.zolo_frondend_mobile.Profile_GUI;
import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.SignUp_GUI;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.chat.MessageActivity;
import com.example.zolo_frondend_mobile.chat.StatusGetOneGroup;
import com.example.zolo_frondend_mobile.chat.StatusGroupSingle;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.GetGroup;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.group.CustomAdapterGroup;
import com.example.zolo_frondend_mobile.group.Group;
import com.example.zolo_frondend_mobile.group.OnClickGroup;
import com.example.zolo_frondend_mobile.group.ProfileGroup_GUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendActivity extends AppCompatActivity implements OnClckFriend, OnClickGroup, OnClickMem {
    RecyclerView rcv_friend;
    CustomAdapterFriend adt;
    List<Friend> mFriends = new ArrayList<>();

    RecyclerView  rcv_member;
    CustomAdapterMember adtMember;
    List<Friend> mMembers = new ArrayList<>();

    RecyclerView rcv_group;
    CustomAdapterGroup adtGroup;
    List<Group> mGroups = new ArrayList<>();

    ImageView imgAdd;
    ImageButton imgbtnNotF, imgbtnAccept, imgbtnProfile;
    List<Long> mAddMem = new ArrayList<>();

    Button btnCreateGroup, btnCancer;
    TextView edtNameGroup;
    ProgressBar progressBar7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        rcv_friend = findViewById(R.id.rcv_friend);
        rcv_member = findViewById(R.id.rcv_member);
        rcv_group = findViewById(R.id.rcv_group);
        imgbtnNotF = findViewById(R.id.imgbtnNotF);
        imgbtnAccept = findViewById(R.id.imgbtnAccept);
        imgbtnProfile = findViewById(R.id.imgbtnProfile);
        btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnCancer = findViewById(R.id.btnCancer);
        imgAdd = findViewById(R.id.imgAdd);
        edtNameGroup = findViewById(R.id.edtNameGroup);
        progressBar7 = findViewById(R.id.progressBar7);
        progressBar7.setVisibility(View.GONE);

        rcv_member.setVisibility(View.GONE);
        btnCreateGroup.setVisibility(View.GONE);
        btnCancer.setVisibility(View.GONE);
        edtNameGroup.setVisibility(View.GONE);

        adt = new CustomAdapterFriend(mFriends, this);
        rcv_friend.setAdapter(adt);
        rcv_friend.setHasFixedSize(true);
        rcv_friend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getFriends();

//        rcv_member.setVisibility(View.GONE);
        adtMember = new CustomAdapterMember(mFriends, this);
        rcv_member.setAdapter(adtMember);
        rcv_member.setHasFixedSize(true);
        rcv_member.setLayoutManager(new LinearLayoutManager(FriendActivity.this, LinearLayoutManager.VERTICAL, false));
        getMembers();

        adtGroup = new CustomAdapterGroup(mGroups, this);
        rcv_group.setAdapter(adtGroup);
        rcv_group.setHasFixedSize(true);
        rcv_group.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getGroups();

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar7.setVisibility(View.VISIBLE);
                ApiHeaderService.apiService.CreatGroup(mAddMem,edtNameGroup.getText().toString().trim()).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200) {
                                Toast.makeText(FriendActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                mGroups = new ArrayList<>();
                                getGroups();
                                mAddMem = new ArrayList<>();
                                progressBar7.setVisibility(View.GONE);
                                finish();
                                startActivity(getIntent());
                                rcv_group.setVisibility(View.VISIBLE);
                                imgAdd.setVisibility(View.VISIBLE);
                            }

                        }else if(response.code() == 404){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(FriendActivity.this, MainActivity.class));
                                progressBar7.setVisibility(View.GONE);
                                finish();
                            }catch(IOException e){
                                Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FriendActivity.this, MainActivity.class));
                                progressBar7.setVisibility(View.GONE);
                                finish();
                            }

                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                progressBar7.setVisibility(View.GONE);
                            }catch(IOException e){
                                Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar7.setVisibility(View.GONE);
                            }
                        }else{
                            Toast.makeText(FriendActivity.this, "create group fail", Toast.LENGTH_SHORT).show();
                            progressBar7.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                    }
                });


            }
        });

        btnCancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddMem = new ArrayList<>();
                rcv_member.setVisibility(View.GONE);
                btnCreateGroup.setVisibility(View.GONE);
                btnCancer.setVisibility(View.GONE);
                edtNameGroup.setVisibility(View.GONE);
                rcv_group.setVisibility(View.VISIBLE);
                imgAdd.setVisibility(View.VISIBLE);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNameGroup.setVisibility(View.VISIBLE);
                rcv_member.setVisibility(View.VISIBLE);
                btnCancer.setVisibility(View.VISIBLE);
                btnCreateGroup.setVisibility(View.VISIBLE);
                getMembers();
                rcv_group.setVisibility(View.GONE);
                imgAdd.setVisibility(View.GONE);
            }
        });

        imgbtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, Profile_GUI.class));
                finish();
            }
        });

        imgbtnNotF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, NotFriendActivity.class));
                finish();
            }
        });
        imgbtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, AcceptFriend1Activity.class));
                finish();
            }
        });
    }


    private void getGroups() {
        progressBar7.setVisibility(View.VISIBLE);
        //get lastId
        int lastId = 0;
        if(mGroups.size() == 0){
            lastId = 0;
        }else{
            lastId = 0;
        }

        ApiHeaderService.apiService.getGroup(25,0).enqueue(new Callback<GetGroup>() {
            @Override
            public void onResponse(Call<GetGroup> call, Response<GetGroup> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        mGroups = response.body().getData();
//                        Toast.makeText(FriendActivity.this, "Get list group success", Toast.LENGTH_SHORT).show();
                        adtGroup.changList(mGroups);
                        progressBar7.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(FriendActivity.this, "Get list group fail", Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                        progressBar7.setVisibility(View.GONE);
                        finish();
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                        progressBar7.setVisibility(View.GONE);
                        finish();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar7.setVisibility(View.GONE);
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }
                }else{

                }

            }

            @Override
            public void onFailure(Call<GetGroup> call, Throwable t) {

            }
        });
    }

    private void getFriends(){
        //get lastId
        int lastId = 0;
        if(mFriends.size() == 0){
            lastId = 0;
        }else{
            lastId = 0;
        }
        //call api
        ApiHeaderService.apiService.getFriends(25, 0).enqueue(new Callback<GetFriend>() {
            @Override
            public void onResponse(Call<GetFriend> call, Response<GetFriend> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 204){
                        mFriends = response.body().getData();
//                        Toast.makeText(FriendActivity.this, "Get list friend success", Toast.LENGTH_SHORT).show();
                        adt.changList(mFriends);
                    }else{
                        Toast.makeText(FriendActivity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFriend> call, Throwable t) {

            }
        });
    }

    @Override
    public void clickItem(Friend f) {
        Intent intent = new Intent(FriendActivity.this, ProfileFriend.class);
        intent.putExtra("friend", f);
        startActivity(intent);
    }

    @Override
    public void buttonTextClick(Friend f) {
        progressBar7.setVisibility(View.VISIBLE);
        Intent intent = new Intent(FriendActivity.this, MessageActivity.class);
        intent.putExtra("friend", f);
        ApiHeaderService.apiService.getGroupSingle(f.getId()).enqueue(new Callback<StatusGroupSingle>() {
            @Override
            public void onResponse(Call<StatusGroupSingle> call, Response<StatusGroupSingle> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        Integer groupId = response.body().getData().getId();
                        ApiHeaderService.apiService.getGroupById(groupId).enqueue(new Callback<StatusGetOneGroup>() {
                            @Override
                            public void onResponse(Call<StatusGetOneGroup> call, Response<StatusGetOneGroup> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getCode() == 200){
                                       Group gr = response.body().getData();
                                       intent.putExtra("group",gr);
                                        startActivity(intent);
                                        progressBar7.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(FriendActivity.this, "Get member friend fail", Toast.LENGTH_SHORT).show();
                                        progressBar7.setVisibility(View.GONE);
                                    }
                                }else if(response.code() == 404){
                                    Gson gson = new GsonBuilder().create();
                                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                                    try{
                                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                                        progressBar7.setVisibility(View.GONE);
                                        finish();
                                    }catch(IOException e){
                                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                                        progressBar7.setVisibility(View.GONE);
                                        finish();
                                    }

                                }else if(response.code() == 400){
                                    Gson gson = new GsonBuilder().create();
                                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                                    try{
                                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar7.setVisibility(View.GONE);
                                    }catch(IOException e){
                                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar7.setVisibility(View.GONE);
                                    }
                                }else{
                                    progressBar7.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusGetOneGroup> call, Throwable t) {

                            }
                        });
                    }else{
                        Toast.makeText(FriendActivity.this, "Get member friend fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                        progressBar7.setVisibility(View.GONE);
                        finish();
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                        progressBar7.setVisibility(View.GONE);
                        finish();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar7.setVisibility(View.GONE);
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }
                }else{
                    progressBar7.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StatusGroupSingle> call, Throwable t) {

            }
        });

    }

    @Override
    public void ClickItem(Group gr) {
        Intent intent = new Intent(FriendActivity.this, ProfileGroup_GUI.class);
        intent.putExtra("group",gr);
        startActivity(intent);
    }

    @Override
    public void ClickButtonText(Group gr) {
        Intent intent = new Intent(FriendActivity.this, MessageActivity.class);
        intent.putExtra("group",gr);
        startActivity(intent);
    }


    private void getMembers(){
        progressBar7.setVisibility(View.VISIBLE);
        //get lastId
        int lastId = 0;
        if(mFriends.size() == 0){
            lastId = 0;
        }else{
            lastId = 0;
        }
        //call api
        ApiHeaderService.apiService.getFriends(25, 0).enqueue(new Callback<GetFriend>() {
            @Override
            public void onResponse(Call<GetFriend> call, Response<GetFriend> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 204){
                        mMembers = response.body().getData();
//                        Toast.makeText(FriendActivity.this, "Get member friend success", Toast.LENGTH_SHORT).show();
                        adtMember.changList(mMembers);
                        progressBar7.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(FriendActivity.this, "Get member friend fail", Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(FriendActivity.this, MainActivity.class));
                        progressBar7.setVisibility(View.GONE);
                        finish();
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(FriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar7.setVisibility(View.GONE);
                    }catch(IOException e){
                        Toast.makeText(FriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar7.setVisibility(View.GONE);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFriend> call, Throwable t) {

            }
        });
    }

    @Override
    public void CliclItemCheckBox(Friend fr, CheckBox checkBox) {
        if(checkBox.isChecked()){
            mAddMem.add(fr.getId());
            Toast.makeText(FriendActivity.this, "da add "+fr.getId(), Toast.LENGTH_SHORT).show();
        }else{
            for(Long id:mAddMem){
                if(id == fr.getId()){
                    mAddMem.remove(id);
                    Toast.makeText(FriendActivity.this, "da move", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}