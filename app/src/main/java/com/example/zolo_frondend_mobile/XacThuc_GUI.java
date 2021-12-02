package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.ResendOTP;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.entity.VerifyOTP;
import com.example.zolo_frondend_mobile.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacThuc_GUI extends AppCompatActivity {
    EditText edtEmail, edtOtp;
    TextView tvDangNhap;
    Button btnVerify, btnResendOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc_gui);
        edtEmail = findViewById(R.id.edtEmail);
        edtOtp = findViewById(R.id.edtOtp);
        btnVerify = findViewById(R.id.btnVerify);
        btnResendOTP = findViewById(R.id.btnResendOTP);
        tvDangNhap = findViewById(R.id.tvDangNhap);

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(XacThuc_GUI.this, MainActivity.class));
            }
        });

        String emailSignUp = getIntent().getStringExtra("email");
        if(emailSignUp != null ){
            edtEmail.setText(emailSignUp);
        }

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                ApiService.apiService.getResendVerifyOTP(new ResendOTP(email)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                Toast.makeText(XacThuc_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(XacThuc_GUI.this, MainActivity.class));
                            }else if(response.body().getCode() != 200){
                                Toast.makeText(XacThuc_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(XacThuc_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(XacThuc_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(XacThuc_GUI.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                    }
                });
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String otp = edtOtp.getText().toString();

                ApiService.apiService.getVerifyOTP(new VerifyOTP(email, otp)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 204){
                                String mess = response.body().getMessage();
                                Toast.makeText(XacThuc_GUI.this, ""+mess, Toast.LENGTH_SHORT).show();
                            }else if(response.body().getCode() != 204){
                                String mess = response.body().getMessage();
                                Toast.makeText(XacThuc_GUI.this, ""+mess, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(XacThuc_GUI.this, "Xác thực không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 400){
                                Gson gson = new GsonBuilder().create();
                                StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                                try{
                                    mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                    Toast.makeText(XacThuc_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                }catch(IOException e){
                                    Toast.makeText(XacThuc_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        }else{
                            Toast.makeText(XacThuc_GUI.this, "Xác thực không thành công", Toast.LENGTH_SHORT).show();
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