package com.example.zolo_frondend_mobile.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.danhsach.CustomAdapterMember;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.danhsach.FriendActivity;
import com.example.zolo_frondend_mobile.danhsach.OnClickMem;
import com.example.zolo_frondend_mobile.danhsach.Status200AddMember;
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

public class ProfileGroup_GUI extends AppCompatActivity implements OnClickMemberGroup, OnClickMem {
    ImageView imgPG;
    TextView tvPGName;
    RecyclerView rcvPG, rcv_mem;
    Button btnPGAdd,btnGPOutGroup, btnPGDeleteGroup;
    Group group;
    ImageButton btnPGTurnBack;

    CustomAdapterGroupMember adt;
    CustomAdapterMember adt_member;
    List<Friend> mFriendGroups = new ArrayList<>();
    List<Friend> mFriends = new ArrayList<>();
    List<Friend> mFriendNotInGroups = new ArrayList<>();
    List<Long> mAddMem = new ArrayList<>();


    Button btnAddMember, btnCancelMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        imgPG = findViewById(R.id.imgPG);
        btnAddMember = findViewById(R.id.btnAddMem);
        btnCancelMember = findViewById(R.id.btnCancelMem);
        rcv_mem = findViewById(R.id.rcv_mem);
        tvPGName = findViewById(R.id.tvPGName);
        btnPGAdd = findViewById(R.id.btnPGAdd);
        rcvPG = findViewById(R.id.rcvPG);
        btnGPOutGroup = findViewById(R.id.btnGPOutGroup);
        btnPGDeleteGroup = findViewById(R.id.btnPGDeleteGroup);
        btnPGTurnBack = findViewById(R.id.btnPGTurnBack);


        adt = new CustomAdapterGroupMember(mFriendGroups, this);
        rcvPG.setAdapter(adt);
        rcvPG.setHasFixedSize(true);
        rcvPG.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adt_member = new CustomAdapterMember(mFriendNotInGroups,this);
        rcv_mem.setAdapter(adt_member);
        rcv_mem.setHasFixedSize(true);
        rcv_mem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        group = (Group) getIntent().getSerializableExtra("group");
        if(group != null){
            imgPG.setImageResource(R.drawable.tuong);
            tvPGName.setText(group.getName());
            getFrienGroups();
        }
        btnPGAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriends();
            }
        });

        btnPGTurnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileGroup_GUI.this, FriendActivity.class));
            }
        });

        rcv_mem.setVisibility(View.GONE);
        btnAddMember.setVisibility(View.GONE);
        btnCancelMember.setVisibility(View.GONE);
        btnPGAdd.setVisibility(View.VISIBLE);

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

        btnPGAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcv_mem.setVisibility(View.VISIBLE);
                btnCancelMember.setVisibility(View.VISIBLE);
                btnAddMember.setVisibility(View.VISIBLE);
                btnGPOutGroup.setVisibility(View.GONE);
                btnPGDeleteGroup.setVisibility(View.GONE);
                getFriends();
                btnPGAdd.setVisibility(View.GONE);
            }
        });

        btnCancelMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddMem = new ArrayList<>();
                rcv_mem.setVisibility(View.GONE);
                btnAddMember.setVisibility(View.GONE);
                btnCancelMember.setVisibility(View.GONE);
                btnPGAdd.setVisibility(View.VISIBLE);
                btnGPOutGroup.setVisibility(View.VISIBLE);
                btnPGDeleteGroup.setVisibility(View.VISIBLE);
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHeaderService.apiService.addMemGroup(mAddMem, group.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                mAddMem = new ArrayList<>();
                                rcv_mem.setVisibility(View.GONE);
                                btnAddMember.setVisibility(View.GONE);
                                btnCancelMember.setVisibility(View.GONE);
                                btnPGAdd.setVisibility(View.VISIBLE);
                                btnGPOutGroup.setVisibility(View.VISIBLE);
                                btnPGDeleteGroup.setVisibility(View.VISIBLE);
                                finish();
                                startActivity(getIntent());
                                Toast.makeText(ProfileGroup_GUI.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProfileGroup_GUI.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
                mAddMem = new ArrayList<>();
                rcv_mem.setVisibility(View.GONE);
                btnAddMember.setVisibility(View.GONE);
                btnCancelMember.setVisibility(View.GONE);
                btnPGAdd.setVisibility(View.VISIBLE);

            }
        });
    }



    private void getFrienGroups(){
        //get lastId
        int lastId = 0;
        if(mFriendGroups.size() == 0){
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
                                mFriendGroups.add(fri);
                            }
                        }

                        Toast.makeText(ProfileGroup_GUI.this, "Get list friend success", Toast.LENGTH_SHORT).show();
                        adt.changList(mFriendGroups);
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

    private void getFriends(){
        mFriendNotInGroups=  new ArrayList<>();
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
                        getFrienGroups();
                        for (Friend fr: mFriends) {
                            Boolean isAdd = true;
                            for (Friend fr1: mFriendGroups) {
                                if(fr.getId() == fr1.getId() || fr.getId() == JWTUtils.USER_ZOLO.getId()){
                                    isAdd = false;
                                }
                            }
                            if(isAdd){
                                mFriendNotInGroups.add(fr);
                            }
                        }
                        adt_member.changList(mFriendNotInGroups);
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

    @Override
    public void CliclItemCheckBox(Friend fr, CheckBox checkBox) {
        if(checkBox.isChecked()){
            mAddMem.add(fr.getId());
            Toast.makeText(ProfileGroup_GUI.this, "da add "+fr.getId(), Toast.LENGTH_SHORT).show();
        }else{
            for(Long id:mAddMem){
                if(id == fr.getId()){
                    mAddMem.remove(id);
                    Toast.makeText(ProfileGroup_GUI.this, "da move", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}