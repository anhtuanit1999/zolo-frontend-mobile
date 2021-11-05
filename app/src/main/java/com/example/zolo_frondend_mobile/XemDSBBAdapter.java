package com.example.zolo_frondend_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class XemDSBBAdapter extends RecyclerView.Adapter<XemDSBBAdapter.ThingViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<XemDSBB> xemDSBBS;

    public XemDSBBAdapter(Context context, ArrayList<XemDSBB>xemDSBBS ) {
        layoutInflater= LayoutInflater.from(context);
        this.xemDSBBS = xemDSBBS;
    }

    public XemDSBBAdapter(ArrayList<XemDSBB>arrayList){
        this.xemDSBBS = arrayList;
    }
    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView= layoutInflater.inflate(R.layout.item_xemdsbanbe, parent, false);
        return new XemDSBBAdapter.ThingViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {

        XemDSBB xemDSBB = xemDSBBS.get(position);
        holder.xemDSBB = xemDSBBS.get(position);
        holder.tvnamebanbe.setText(xemDSBB.getTvnamebanbe());
        holder.imgbanbe.setImageResource(xemDSBB.getImg_BanBe());
        //holder.viewReCy.setId(recyclerViewActivity.getViewrecycle());

    }

    @Override
    public int getItemCount() {

        return xemDSBBS.size();
    }

    public class ThingViewHolder extends  RecyclerView.ViewHolder{
        XemDSBB xemDSBB;
        TextView tvnamebanbe;

        ImageView imgbanbe;
        XemDSBBAdapter xemDSBBAdapter;
        public ThingViewHolder(@NonNull View itemView, XemDSBBAdapter adapter) {
            super(itemView);

            tvnamebanbe = itemView.findViewById(R.id.tv_xemdsbanbe);
            //viewReCy = itemView.findViewById(R.id.viewRecycle);
            imgbanbe= itemView.findViewById(R.id.img_xemdsBB);

            this.xemDSBBAdapter=adapter;

        }
    }
}
