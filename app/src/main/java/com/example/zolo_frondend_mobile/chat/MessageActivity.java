package com.example.zolo_frondend_mobile.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zolo_frondend_mobile.R;

public class MessageActivity extends AppCompatActivity {
    ImageButton btnImgSend;
    TextView textsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        btnImgSend = findViewById(R.id.btn_send);
        textsend = findViewById(R.id.textsend);

        btnImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textsend.getText().toString();
                if(msg.trim().equals("")){
                    Toast.makeText(MessageActivity.this, "Type message", Toast.LENGTH_SHORT).show();
                }else{
                    //luu du lieu, tai lai du lieu
                }
                textsend.setText("");
            }
        });
    }
}