package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.danhsach.FriendActivity;
import com.example.zolo_frondend_mobile.entity.ResendOTP;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_GUI extends AppCompatActivity {
    Button btnSignOut, btnNewPassword;
    TextView tvPPhone, tvPName;
    ImageView imgP;
    ProgressBar progressBar;
    ImageButton imgbtnPHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_gui);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnNewPassword = findViewById(R.id.btnNewPassword);
        tvPPhone = findViewById(R.id.tvPPhone);
        tvPName = findViewById(R.id.tvPName);
        imgP = findViewById(R.id.imgP);
        imgbtnPHome = findViewById(R.id.imgbtnPHome);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        imgbtnPHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_GUI.this, FriendActivity.class));
                finish();
            }
        });

        tvPName.setText(JWTUtils.USER_ZOLO.getFullName());
        tvPPhone.setText(JWTUtils.USER_ZOLO.getPhone());
        imgP.setImageResource(R.drawable.tuong);

        btnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
//                ApiService.apiService.getResendVerifyOTP(new ResendOTP(JWTUtils.EMAIL)).enqueue(new Callback<StatusCode204VerifyOTP>() {
//                    @Override
//                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
//                        if(response.isSuccessful()){
//                            if(response.body().getCode() == 200){
//                                Toast.makeText(Profile_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Profile_GUI.this, ChangePassword_GUI.class));
//                            }else if(response.body().getCode() != 200){
//                                Toast.makeText(Profile_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }else if(response.code() == 400){
//                            Gson gson = new GsonBuilder().create();
//                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
//                            try{
//                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
//                                Toast.makeText(Profile_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                            }catch(IOException e){
//                                Toast.makeText(Profile_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(Profile_GUI.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {
//
//                    }
//                });
                startActivity(new Intent(Profile_GUI.this, ChangePassword_GUI.class));
                progressBar.setVisibility(View.GONE);
                finish();
            }

        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ApiService.apiService.getSignout("Bearer "+JWTUtils.TOKEN).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 204){
                                Toast.makeText(Profile_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                                finish();
                            }else if(response.body().getCode() == 404){
                                Toast.makeText(Profile_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        }else if(response.code() == 401){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(Profile_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }catch(IOException e){
                                Toast.makeText(Profile_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        }else{
                            Toast.makeText(Profile_GUI.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {
                        startActivity(new Intent(Profile_GUI.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                });
            }
        });
    }
}