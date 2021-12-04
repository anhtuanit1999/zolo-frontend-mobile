package com.example.zolo_frondend_mobile.group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.zolo_frondend_mobile.danhsach.CustomAdapterFriend;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.danhsach.FriendActivity;
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
    Button btnPGAdd;
    Group group;

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

    }
}