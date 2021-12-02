package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.entity.ResendOTP;
import com.example.zolo_frondend_mobile.entity.SignIn;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignIn;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.model.UserMockAPI;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    Button btnDangki, btnDangnhap, btnVerifyOTP;
    TextView txt_QuenMatKhau;
    EditText edtEmail, edtPassword;
    List<UserMockAPI> list = new ArrayList<>();
    // thieu khai bao a`
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDangki = findViewById(R.id.btnDangki);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnDangnhap = findViewById(R.id.btnDangNhap);
        btnDangki = findViewById(R.id.btnDangki);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        txt_QuenMatKhau= findViewById(R.id.txt_QuenMatKhau);

        String email = getIntent().getStringExtra("email");
        if(email != null){
            edtEmail.setText(email);
        }

        txt_QuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgorPassWord_GUI.class);
                intent.putExtra("email",edtEmail.getText().toString());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "aaaaaa", Toast.LENGTH_SHORT).show();
//                ApiService.apiService.getForgotPassWordinMain(edtEmail.getText().toString()).enqueue(new Callback<StatusCode204VerifyOTP>() {
//                    @Override
//                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
//                        if(response.isSuccessful()){
//                            if(response.body().getCode() == 200){
//                                Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(MainActivity.this, ForgorPassWord_GUI.class));
//                            }else if(response.body().getCode() != 200){
//                                Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }else if(response.code() == 400){
//                            Gson gson = new GsonBuilder().create();
//                            StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
//                            try{
//                                mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
//                                Toast.makeText(MainActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                            }catch(IOException e){
//                                Toast.makeText(MainActivity.this, "err: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {
//
//                    }
//                });
            }
        });

        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XacThuc_GUI.class));
            }
        });

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                SignIn object = new SignIn(email, password);
                Gson gson = new Gson();
                String signin = gson.toJson(object);
                ApiService.apiService.getUserSignIn(new SignIn(email, password)).enqueue(new Callback<StatusCode200SignIn>() {
                    @Override
                    public void onResponse(Call<StatusCode200SignIn> call, Response<StatusCode200SignIn> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200 ){
                                Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                                StatusCode200SignIn a = response.body();
                                JWTUtils.TOKEN = a.getData().getJwt();
                                JWTUtils.EMAIL = a.getData().getEmail();
                                JWTUtils.USER_ZOLO = a.getData();
                                Toast.makeText(MainActivity.this, "Dang nhap thanh cong: "+JWTUtils.TOKEN, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Profile_GUI.class));
                            }else if(response.body().getCode() != 200){
                                ApiService.apiService.getUserSignInFail(new SignIn(email, password)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                                    @Override
                                    public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response) {
                                        Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                                    }
                                });
                            }else{
                                Toast.makeText(MainActivity.this, "Sign In Fail", Toast.LENGTH_SHORT).show();
                            }
                            
                        }else{
                            ApiService.apiService.getUserSignInFail(new SignIn(email, password)).enqueue(new Callback<StatusCode204VerifyOTP>() {
                                @Override
                                public void onResponse(Call<StatusCode204VerifyOTP> call, Response<StatusCode204VerifyOTP> response1) {
                                    if(response1.code() == 400){
                                        Gson gson = new GsonBuilder().create();
                                        StatusCode204VerifyOTP mError = new StatusCode204VerifyOTP();
                                        try{
                                            mError= gson.fromJson(response.errorBody().string(),StatusCode204VerifyOTP.class);
                                            Toast.makeText(MainActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                                        }catch(IOException e){

                                        }
//                                        Toast.makeText(SignUp_GUI.this, "aaa"+response1.errorBody(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<StatusCode204VerifyOTP> call, Throwable t) {

                                }
                            });
//                            Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không đúng "+response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusCode200SignIn> call, Throwable t) {
                        Log.e("a",t.getMessage());
                        Toast.makeText(MainActivity.this, "err: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp_GUI.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            Intent intent  = new Intent(MainActivity.this, XemDSBanBeActivity.class);
            startActivity(intent);
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}