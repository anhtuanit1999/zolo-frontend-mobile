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
import com.example.zolo_frondend_mobile.api.ApiServiceMockAPi;
import com.example.zolo_frondend_mobile.entity.SignIn;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignIn;
import com.example.zolo_frondend_mobile.model.UserMockAPI;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnDangki, btnDangnhap;
    EditText edtEmail, edtPassword;
    List<UserMockAPI> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDangki = findViewById(R.id.btnDangki);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnDangnhap = findViewById(R.id.btnDangNhap);
        btnDangki = findViewById(R.id.btnDangki);

//        btnDangnhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadList();
//                String email = edtEmail.getText().toString();
//                String password = edtPassword.getText().toString();
//                boolean isSuccess = false;
//                for (UserMockAPI user: list) {
//                    if(password.equals(user.getMatkhau()) && email.equals(user.getEmail())){
//                        Toast.makeText(MainActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
//                        isSuccess = true;
//                    }
//                }
//                if(isSuccess){
//                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                SignIn object = new SignIn(email, password);
                Gson gson = new Gson();
                String signin = gson.toJson(object);
//                ApiService.apiService.getUserSignIna(email, password).enqueue(new Callback<StatusCode200SignIn>() {
//                    @Override
//                    public void onResponse(Call<StatusCode200SignIn> call, Response<StatusCode200SignIn> response) {
//                        if(response.isSuccessful()){
//                            Log.e("a",response+"");
//                            Toast.makeText(MainActivity.this, "aaa"+response.body(), Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(MainActivity.this, "Khong thanh cong "+response.body(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StatusCode200SignIn> call, Throwable t) {
//                        Toast.makeText(MainActivity.this, "err: "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
                ApiService.apiService.getUserSignIn(new SignIn(email, password)).enqueue(new Callback<StatusCode200SignIn>() {
                    @Override
                    public void onResponse(Call<StatusCode200SignIn> call, Response<StatusCode200SignIn> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 200 ){
                                Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                                StatusCode200SignIn a = response.body();
                                JWTUtils.TOKEN = a.getData().getJwt();
                                Toast.makeText(MainActivity.this, "Dang nhap thanh cong: "+JWTUtils.TOKEN, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                            
                        }else{
                            Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu không đúng "+response.body(), Toast.LENGTH_SHORT).show();
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

    private void loadList(){
        ApiServiceMockAPi.apiService.getList().enqueue(new Callback<List<UserMockAPI>>() {
            @Override
            public void onResponse(Call<List<UserMockAPI>> call, Response<List<UserMockAPI>> response) {
                list = response.body();
                if(list != null && response.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "a: "+list, Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(MainActivity.this, "loi ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserMockAPI>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "err: "+t.getMessage(), Toast.LENGTH_SHORT).show();
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