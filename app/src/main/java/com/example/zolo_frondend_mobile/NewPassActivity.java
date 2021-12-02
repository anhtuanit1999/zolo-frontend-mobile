package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassActivity extends AppCompatActivity {
    EditText edtNEmail, edtNOTP, edtNNewPass;
    Button btnNewPas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        edtNEmail = findViewById(R.id.edtNEmail);
        edtNOTP = findViewById(R.id.edtNOTP);
        edtNNewPass = findViewById(R.id.edtNNewPass);
        btnNewPas = findViewById(R.id.btnNewPas);

        String email = getIntent().getStringExtra("email");
        if(email != null){
            edtNEmail.setText(email);
        }

        btnNewPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> changes = new HashMap<>();
                changes.put("email", edtNEmail.getText().toString());
                changes.put("otp", edtNOTP.getText().toString());
                changes.put("password", edtNNewPass.getText().toString());
                ApiService.apiService.getNNewPassword(changes).enqueue(new Callback<StatusCode204VerifyOTP>() {
                    @Override
                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200){
                                String mess = "Mật khẩu mới đã được cập nhật";
                                if(response.body().getMessage().equals("Mật khẩu mới đã được cập nhật")){
                                    Toast.makeText(NewPassActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(NewPassActivity.this, MainActivity.class);
                                    intent.putExtra("email",edtNEmail.getText().toString());
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(NewPassActivity.this, "Message: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }else if(response.body().getCode() == 401){
                                Toast.makeText(NewPassActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                            try{
                                mError= gson.fromJson(response.errorBody().string(), StatusCode204VerifyOTP.class);
                                Toast.makeText(NewPassActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            }catch(IOException e){
                                Toast.makeText(NewPassActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(NewPassActivity.this, "Loi", Toast.LENGTH_SHORT).show();
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