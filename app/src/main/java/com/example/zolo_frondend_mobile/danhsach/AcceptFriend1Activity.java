package com.example.zolo_frondend_mobile.danhsach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.MainActivity;
import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptFriend1Activity extends AppCompatActivity implements OnClickAddFriend{
    RecyclerView rcv_accept;
    private CustomAdapterAcceptFriend adt;
    List<Friend> mAFriends = new ArrayList<>();
    TextView tv_addF;
    ProgressBar progressBar9;
    ImageButton imgBtnAHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_friend1);
        rcv_accept = findViewById(R.id.rcv_accept1);
        imgBtnAHome = findViewById(R.id.imgBtnAHome);
        progressBar9 = findViewById(R.id.progressBar9);
        progressBar9.setVisibility(View.GONE);
        tv_addF = findViewById(R.id.tv_addF);
        tv_addF.setVisibility(View.GONE);
//        getAllUsers();
        adt = new CustomAdapterAcceptFriend(mAFriends, this);
        rcv_accept.setAdapter(adt);
        rcv_accept.setHasFixedSize(true);
        rcv_accept.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getAFriends();

        imgBtnAHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcceptFriend1Activity.this, FriendActivity.class));
                finish();
            }
        });
    }
    private void getAFriends(){
        progressBar9.setVisibility(View.VISIBLE);
        //get lastId
        int lastId = 0;
        if(mAFriends.size() == 0){
            lastId = 0;
        }else{
            lastId = 0;
        }
        //call api
        ApiHeaderService.apiService.getInviteFriends(25, 0).enqueue(new Callback<GetFriend>() {
            @Override
            public void onResponse(Call<GetFriend> call, Response<GetFriend> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 204){
                        mAFriends = response.body().getData();
//                        Toast.makeText(AcceptFriend1Activity.this, "Get list friend success"+mAFriends, Toast.LENGTH_SHORT).show();
                        adt.changList(mAFriends);
                        progressBar9.setVisibility(View.GONE);
                    }else if(response.body().getCode() == 200){
                        tv_addF.setVisibility(View.VISIBLE);
                        progressBar9.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(AcceptFriend1Activity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                        progressBar9.setVisibility(View.GONE);
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();

                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AcceptFriend1Activity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFriend> call, Throwable t) {
                Toast.makeText(AcceptFriend1Activity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void ButtonAcceptFriendClick(Friend f) {
        progressBar9.setVisibility(View.VISIBLE);
        ApiHeaderService.apiService.AcceptOrDeleteFriend(f.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
            @Override
            public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200) {
                        Toast.makeText(AcceptFriend1Activity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mAFriends = new ArrayList<>();
                        getAFriends();
                        progressBar9.setVisibility(View.GONE);
                        finish();
                        startActivity(getIntent());
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();
                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar9.setVisibility(View.GONE);
                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar9.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(AcceptFriend1Activity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                    progressBar9.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

            }
        });
    }

    @Override
    public void ButtonDenyFriendClick(Friend f) {
        progressBar9.setVisibility(View.VISIBLE);
        ApiHeaderService.apiService.denyFriend(f.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
            @Override
            public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200) {
                        Toast.makeText(AcceptFriend1Activity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mAFriends = new ArrayList<>();
                        getAFriends();
                        progressBar9.setVisibility(View.GONE);
                        finish();
                        startActivity(getIntent());
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();
                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AcceptFriend1Activity.this, MainActivity.class));
                        progressBar9.setVisibility(View.GONE);
                        finish();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(AcceptFriend1Activity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar9.setVisibility(View.GONE);
                    }catch(IOException e){
                        Toast.makeText(AcceptFriend1Activity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar9.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(AcceptFriend1Activity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                    progressBar9.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

            }
        });

    }
}