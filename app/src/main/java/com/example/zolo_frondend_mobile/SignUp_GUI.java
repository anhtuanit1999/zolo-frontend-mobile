package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.api.ApiService;
import com.example.zolo_frondend_mobile.api.ApiServiceMockAPi;
import com.example.zolo_frondend_mobile.model.UserMockAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_GUI extends AppCompatActivity {
    List<UserMockAPI> list = new ArrayList<>();
    EditText edtSDT, edtMk, edtTen, edtTuoi;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSDT = findViewById(R.id.edtEmail);
        edtMk = findViewById(R.id.edtPassword);
        edtTen = findViewById(R.id.edtTen);
        edtTuoi = findViewById(R.id.edtTuoi);
        btnSignUp = findViewById(R.id.btnDangki);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadList();
                String email = edtSDT.getText().toString();
                String mk = edtMk.getText().toString();
                String ten = edtTen.getText().toString();
                String tuoi = edtTuoi.getText().toString();
                ApiServiceMockAPi.apiService.addUser(new UserMockAPI(list.size()+1+"",email, mk, Integer.parseInt(tuoi), ten)).enqueue(new Callback<UserMockAPI>() {
                    @Override
                    public void onResponse(Call<UserMockAPI> call, Response<UserMockAPI> response) {
                        Toast.makeText(SignUp_GUI.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp_GUI.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<UserMockAPI> call, Throwable t) {
                        Toast.makeText(SignUp_GUI.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
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
                Toast.makeText(SignUp_GUI.this, "err: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}