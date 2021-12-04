package com.example.zolo_frondend_mobile.danhsach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.SignUp_GUI;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotFriendActivity extends AppCompatActivity implements OnClickNotFriend{
    RecyclerView rcv_notF;
    private CustomAdapterNotFriend adt;
    List<Friend> mNotFriends = new ArrayList<>();
    List<Friend> mFriends = new ArrayList<>();
    List<Friend> mAllUsers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_friend);
        rcv_notF = findViewById(R.id.rcv_notF);
//        getAllUsers();
        adt = new CustomAdapterNotFriend(mNotFriends, this);
        rcv_notF.setAdapter(adt);
        rcv_notF.setHasFixedSize(true);
        rcv_notF.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getNotFriends();

    }


    private void getNotFriends(){
        //get lastId
        int lastId = 0;
        if(mNotFriends.size() == 0){
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
                        Toast.makeText(NotFriendActivity.this, "Get list friend success"+mFriends, Toast.LENGTH_SHORT).show();
                        //dua du lieu tat car user vao
                        getAllUsers();

                    }else{
                        Toast.makeText(NotFriendActivity.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(NotFriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(NotFriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(NotFriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(NotFriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFriend> call, Throwable t) {

            }
        });
    }

    private void getAllUsers(){
        //call api
        ApiService.apiService.getAllUsers().enqueue(new Callback<GetFriend>() {
            @Override
            public void onResponse(Call<GetFriend> call, Response<GetFriend> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        mNotFriends = new ArrayList<>();
                        mAllUsers = response.body().getData();
//                        adt.changList(mNotFriends);
                        Toast.makeText(NotFriendActivity.this, "bbbbbbb"+mAllUsers, Toast.LENGTH_SHORT).show();
                        //xu ly de lay cac listNot friend
                        for(Friend user : mAllUsers){
                            Boolean isFriend = false;
                            for (Friend f : mFriends) {
                                if(f.getId() == user.getId() || user.getId() == JWTUtils.USER_ZOLO.getId()){
                                    isFriend = true;
                                }
                            }
                            if(isFriend == false){
                                mNotFriends.add(user);
                            }
                        }
                        adt.changList(mNotFriends);
                    }else{
                        Toast.makeText(NotFriendActivity.this, "Get Users Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFriend> call, Throwable t) {
                Toast.makeText(NotFriendActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void ButtonAddFriendClick(Friend f) {
        ApiHeaderService.apiService.inviteFriend(f.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
            @Override
            public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NotFriendActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(NotFriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(NotFriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(NotFriendActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(NotFriendActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

            }
        });
    }
}