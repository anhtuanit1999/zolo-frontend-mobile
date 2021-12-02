package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiHeaderService;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword_GUI extends AppCompatActivity {

    EditText edtNewPassword, edtOTP;
    Button btnChangePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_gui);
        edtNewPassword = findViewById(R.id.edtNewPass);
        btnChangePass = findViewById(R.id.btnChangePass);
        edtOTP = findViewById(R.id.edtOTP);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> changes = new HashMap<>();
                changes.put("email", JWTUtils.EMAIL);
                changes.put("otp", edtOTP.getText().toString());
                changes.put("password", edtNewPassword.getText().toString());
                ApiHeaderService.apiService.getNewPassword(changes).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 204){
                                Toast.makeText(ChangePassword_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangePassword_GUI.this, Profile_GUI.class));
                            }else if(response.body().getCode() == 401){
                                Toast.makeText(ChangePassword_GUI.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ChangePassword_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ChangePassword_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 401){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                Toast.makeText(ChangePassword_GUI.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(ChangePassword_GUI.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ChangePassword_GUI.this, "Loi", Toast.LENGTH_SHORT).show();
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