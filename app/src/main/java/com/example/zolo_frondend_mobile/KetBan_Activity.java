package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class KetBan_Activity extends AppCompatActivity {
    private RecyclerView rcvKetban;
    private KetBanAdapter ketBanAdapter;
    private ArrayList<Ketban> ketbans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_ban);

        rcvKetban = findViewById(R.id.rcv_ketban);
        ketbans = new ArrayList<>();
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));
        ketbans.add(new Ketban("Dang An", R.id.btn_ketban, R.drawable.image_ketban));

       ketBanAdapter = new KetBanAdapter(this, ketbans);
        rcvKetban.setAdapter(ketBanAdapter);
        rcvKetban.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvKetban.setLayoutManager(linearLayoutManager);
    }
}