package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.SignUp;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignUp;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.model.User;
import com.example.zolo_frondend_mobile.model.UserMockAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_GUI extends AppCompatActivity {
    List<UserMockAPI> list = new ArrayList<>();
    EditText edtEmail, edtPass, edtFullName, edtNickName, edtPhone;
    Button btnSignUp, btnXacThuc;
    ProgressBar progressBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        edtFullName = findViewById(R.id.edtFullName);
        edtNickName = findViewById(R.id.edtNickName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignUp = findViewById(R.id.btnDangki);
        btnXacThuc = findViewById(R.id.btnXacThuc);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar4.setVisibility(View.GONE);

        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar4.setVisibility(View.VISIBLE);
                startActivity(new Intent(SignUp_GUI.this, XacThuc_GUI.class));
                progressBar4.setVisibility(View.GONE);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar4.setVisibility(View.VISIBLE);
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                String fullname = edtFullName.getText().toString();
                String nickname = edtNickName.getText().toString();
                String phone = edtPhone.getText().toString();
                ApiService.apiService.getUserSignUp(new SignUp(phone,nickname,fullname,email, pass,1)).enqueue(new Callback<StatusCode200SignUp>() {
                    @Override
                    public void onResponse(Call<StatusCode200SignUp> call, Response<StatusCode200SignUp> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                User user = response.body().getData();
                                Toast.makeText(SignUp_GUI.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp_GUI.this, XacThuc_GUI.class);
                                intent.putExtra("email",user.getEmail());
                                startActivity(intent);
                                progressBar4.setVisibility(View.GONE);
                            }else if(response.body().getCode() != 200){
                                ApiService.apiService.getUserSignUpFail(new SignUp(phone,nickname,fullname,email, pass,1)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                                    @Override
                                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response1) {
                                        Toast.makeText(SignUp_GUI.this, ""+response1.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        progressBar4.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                                    }
                                });

                            }else{
                                Toast.makeText(SignUp_GUI.this, "Đăng kí không hợp lệ ", Toast.LENGTH_SHORT).show();
                                progressBar4.setVisibility(View.GONE);
                            }
                        }else{
                            ApiService.apiService.getUserSignUpFail(new SignUp(phone,nickname,fullname,email, pass,1)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                                @Override
                                public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response1) {
                                    if(response1.code() == 400){
                                        Gson gson = new GsonBuilder().create();
                                        StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                                        try{
                                            mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                            Toast.makeText(SignUp_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                            progressBar4.setVisibility(View.GONE);
                                        }catch(IOException e){
                                            Toast.makeText(SignUp_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar4.setVisibility(View.GONE);
                                        }
//                                        Toast.makeText(SignUp_GUI.this, "aaa"+response1.errorBody(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                                }
                            });
//                            Toast.makeText(SignUp_GUI.this, "Đăng kí không hợp lệ "+response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode200SignUp> call, Throwable t) {

                    }
                });
            }
        });



    }


}