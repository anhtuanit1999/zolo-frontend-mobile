package com.example.zolo_frondend_mobile.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolo_frondend_mobile.R;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.danhsach.OnClckFriend;

import java.util.List;

public class CustomAdapterGroup extends RecyclerView.Adapter<CustomAdapterGroup.ViewHolder> {
    List<Group> mGroups;
    OnClickGroup mOnClickGroup;

    public CustomAdapterGroup(List<Group> groups, OnClickGroup onClckFriend) {
        mGroups = groups;
        mOnClickGroup = onClckFriend;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group gr = mGroups.get(position);
        holder.mGroup = gr;
        holder.imgGroup.setImageResource(R.drawable.tuong);
        holder.tvGName.setText(gr.getName());
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public void changList(List<Group> notFriends) {
        mGroups = notFriends;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Group mGroup;
        int position;
        ImageView imgGroup;
        TextView tvGName;
        Button btnGText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGroup = itemView.findViewById(R.id.imgGroup);
            tvGName = itemView.findViewById(R.id.tvGName);
            btnGText = itemView.findViewById(R.id.btnGText);
            btnGText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickGroup.ClickButtonText(mGroup);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickGroup.ClickItem(mGroup);
                }
            });
        }
    }
}
