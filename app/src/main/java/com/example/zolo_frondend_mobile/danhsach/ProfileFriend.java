package com.example.zolo_frondend_mobile.danhsach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFriend extends AppCompatActivity {
    ImageView imgPF;
    TextView tvPFNickName, tvPFPhone;
    Button btnDeleteF;
    Friend mFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_friend);
        btnDeleteF = findViewById(R.id.btnDeleteF);
        imgPF = findViewById(R.id.imgPF);
        tvPFNickName = findViewById(R.id.tvPFNickNam);
        tvPFPhone = findViewById(R.id.tvPFPhone);

        mFriend = (Friend) getIntent().getSerializableExtra("friend");
        if(mFriend != null){
            imgPF.setImageResource(R.drawable.tuong);
            tvPFNickName.setText(mFriend.getNickname());
            tvPFPhone.setText(mFriend.getPhone());
        }

        btnDeleteF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiHeaderService.apiService.AcceptOrDeleteFriend(mFriend.getId()).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200) {
                                Toast.makeText(ProfileFriend.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProfileFriend.this, FriendActivity.class));
                            }

                        }else if(response.code() == 404){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(), StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileFriend.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileFriend.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ProfileFriend.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ProfileFriend.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ProfileFriend.this, "Get list friend fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                    }
                });
            }
        });

    }
}