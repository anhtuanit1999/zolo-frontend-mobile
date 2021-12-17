package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgorPassWord_GUI extends AppCompatActivity {

    TextView edtFEmail;
    Button btnFOTP;
    ProgressBar progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgor_pass_word_gui);
        edtFEmail =  findViewById(R.id.edtFEmail);
        btnFOTP =  findViewById(R.id.btnFOTP);
        progressBar2 =  findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        String email = getIntent().getStringExtra("email");
        if(email != null){
            edtFEmail.setText(email);
        }

        btnFOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                ApiService.apiService.getForgotPassWordinMain(edtFEmail.getText().toString()).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                String mess = "Mã OTP cập nhật mật khẩu mới đã được gửi thành công";
                                if(response.body().getMessage().equals(mess)){
                                    Toast.makeText(ForgorPassWord_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgorPassWord_GUI.this, NewPassActivity.class);
                                    intent.putExtra("email", edtFEmail.getText().toString());
                                    startActivity(intent);
                                    progressBar2.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(ForgorPassWord_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else if(response.body().getCode() == 400){
                                Toast.makeText(ForgorPassWord_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ForgorPassWord_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ForgorPassWord_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 401){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ForgorPassWord_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ForgorPassWord_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
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