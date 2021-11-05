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

public class KetBanAdapter extends RecyclerView.Adapter<KetBanAdapter.ThingViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Ketban> ketbans;

    public KetBanAdapter(Context context, ArrayList<Ketban> ketbans) {
        layoutInflater= LayoutInflater.from(context);
        this.ketbans = ketbans;
    }

    public KetBanAdapter(ArrayList<Ketban>arrayList){
        this.ketbans = arrayList;
    }
    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView= layoutInflater.inflate(R.layout.item_ketban, parent, false);
        return new ThingViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {

        Ketban ketban = ketbans.get(position);
        holder.ketban = ketbans.get(position);
        holder.tvnamketban.setText(ketban.getTvNameKetBan());
        holder.btnketban.setId(ketban.getBtnKetBan());
        holder.imgketban.setImageResource(ketban.getImg_KetBan());
        //holder.viewReCy.setId(recyclerViewActivity.getViewrecycle());

    }

    @Override
    public int getItemCount() {

        return ketbans.size();
    }

    public class ThingViewHolder extends  RecyclerView.ViewHolder{
        Ketban ketban;
        TextView tvnamketban;
        Button btnketban;
        ImageView imgketban;
        KetBanAdapter ketBanAdapter;
        public ThingViewHolder(@NonNull View itemView, KetBanAdapter adapter) {
            super(itemView);

            tvnamketban = itemView.findViewById(R.id.tv_name_ketban);
            btnketban = itemView.findViewById(R.id.btn_ketban);
            //viewReCy = itemView.findViewById(R.id.viewRecycle);
            imgketban= itemView.findViewById(R.id.img_ketban);

            this.ketBanAdapter=adapter;
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    listener.itemClick(getAdapterPosition(),imgTayLoy, tvTayLoy,tvLove);
//                }
//            });

        }
    }
}
