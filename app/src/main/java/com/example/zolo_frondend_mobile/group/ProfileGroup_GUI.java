package com.example.zolo_frondend_mobile.group;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolo_frondend_mobile.ChangePassword_GUI;
import com.example.zolo_frondend_mobile.MainActivity;
import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.chat.MessageActivity;
import com.example.zolo_frondend_mobile.danhsach.CustomAdapterFriend;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.danhsach.FriendActivity;
import com.example.zolo_frondend_mobile.danhsach.Status200AddMember;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.ResendOTP;
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

public class ProfileGroup_GUI extends AppCompatActivity implements OnClickMemberGroup{
    ImageView imgPG;
    TextView tvPGName;
    RecyclerView rcvPG;
    Button btnPGAdd,btnGPOutGroup, btnPGDeleteGroup;
    Group group;
    ImageButton btnPGTurnBack;

    CustomAdapterGroupMember adt;
    List<Friend> mFriends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        imgPG = findViewById(R.id.imgPG);
        tvPGName = findViewById(R.id.tvPGName);
        btnPGAdd = findViewById(R.id.btnPGAdd);
        rcvPG = findViewById(R.id.rcvPG);
        btnGPOutGroup = findViewById(R.id.btnGPOutGroup);
        btnPGDeleteGroup = findViewById(R.id.btnPGDeleteGroup);
        btnPGTurnBack = findViewById(R.id.btnPGTurnBack);


        adt = new CustomAdapterGroupMember(mFriends, this);
        rcvPG.setAdapter(adt);
        rcvPG.setHasFixedSize(true);
        rcvPG.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        group = (Group) getIntent().getSerializableExtra("group");
        if(group != null){
            imgPG.setImageResource(R.drawable.tuong);
            tvPGName.setText(group.getName());
            getFriends();
        }
        btnPGAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnPGTurnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileGroup_GUI.this, FriendActivity.class));
            }
        });

        btnGPOutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHeaderService.apiService.deleteMemberGroup(group.getId(),JWTUtils.USER_ZOLO.getId()).enqueue(new Callback<Status200AddMember>() {
                    @Override
                    public void onResponse(Call<Status200AddMember> call, Response<Status200AddMember> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                Toast.makeText(ProfileGroup_GUI.this, "kick thanh cong", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }else{
                                Toast.makeText(ProfileGroup_GUI.this, "kick  fail", Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.code() == 404){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<Status200AddMember> call, Throwable t) {

                    }
                });
            }
        });

        btnPGDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHeaderService.apiService.deleteGroup(group.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                Toast.makeText(ProfileGroup_GUI.this, "Delete group thanh cong", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ProfileGroup_GUI.this, FriendActivity.class));
                            }else{
                                Toast.makeText(ProfileGroup_GUI.this, "Delete group  fail" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.code() == 404){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                    }
                });
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
        ApiHeaderService.apiService.getGroupMember(group.getId()).enqueue(new Callback<GetFriend>() {
            @Override
            public void onResponse(Call<GetFriend> call, Response<GetFriend> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        List<Friend> friends = new ArrayList<>();
                        friends = response.body().getData();

                        for (Friend fri:friends) {
                            Boolean isUser = false;
                            if(fri.getId() == JWTUtils.USER_ZOLO.getId()){
                                isUser =true;
                            }
                            if(!isUser){
                                mFriends.add(fri);
                            }
                        }

                        Toast.makeText(ProfileGroup_GUI.this, "Get list friend success", Toast.LENGTH_SHORT).show();
                        adt.changList(mFriends);
                    }else{
                        Toast.makeText(ProfileGroup_GUI.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void ClickButton(Friend fr) {
        ApiHeaderService.apiService.deleteMemberGroup(group.getId(),Integer.parseInt(String.valueOf(fr.getId()))).enqueue(new Callback<Status200AddMember>() {
            @Override
            public void onResponse(Call<Status200AddMember> call, Response<Status200AddMember> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        Toast.makeText(ProfileGroup_GUI.this, "kick thanh cong", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }else{
                        Toast.makeText(ProfileGroup_GUI.this, "kick  fail", Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 404){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else if(response.code() == 400){
                    Gson gson = new GsonBuilder().create();
                    StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                    try{
                        mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                        Toast.makeText(ProfileGroup_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(IOException e){
                        Toast.makeText(ProfileGroup_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<Status200AddMember> call, Throwable t) {

            }
        });
    }
}