package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUp_GUI extends AppCompatActivity {
    EditText edtSDT, edtMk, edtTen, edtTuoi;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSDT = findViewById(R.id.edtSDT);
        edtMk = findViewById(R.id.edtMk);
        edtTen = findViewById(R.id.edtTen);
        edtTuoi = findViewById(R.id.edtTuoi);


    }
}