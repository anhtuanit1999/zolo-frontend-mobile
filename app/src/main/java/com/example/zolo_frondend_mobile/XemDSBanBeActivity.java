package com.example.zolo_frondend_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class XemDSBanBeActivity extends AppCompatActivity {
    private RecyclerView rcvDSBB;
    private XemDSBBAdapter xemDSBBAdapter;
    private ArrayList<XemDSBB> xemDSBBS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_dsban_be);

        rcvDSBB = findViewById(R.id.rcv_XemDSBB);
        xemDSBBS = new ArrayList<>();
        xemDSBBS.add(new XemDSBB("Dang An", R.drawable.image_ketban));
        xemDSBBS.add(new XemDSBB("Huy Hao", R.drawable.image_dsbb));
        xemDSBBS.add(new XemDSBB("Dang An", R.drawable.image_ketban));
        xemDSBBS.add(new XemDSBB("Huy Hao", R.drawable.image_dsbb));
        xemDSBBS.add(new XemDSBB("Dang An", R.drawable.image_ketban));
        xemDSBBS.add(new XemDSBB("Huy Hao", R.drawable.image_dsbb));

        xemDSBBAdapter = new XemDSBBAdapter(this, xemDSBBS);
        rcvDSBB.setAdapter(xemDSBBAdapter);
        rcvDSBB.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDSBB.setLayoutManager(linearLayoutManager);
    }

}