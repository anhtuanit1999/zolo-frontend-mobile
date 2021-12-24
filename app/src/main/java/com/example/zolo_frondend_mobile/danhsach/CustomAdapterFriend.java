package com.example.zolo_frondend_mobile.danhsach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zolo_frondend_mobile.KetBanAdapter;
import com.example.zolo_frondend_mobile.R;

import java.util.List;

public class CustomAdapterFriend extends RecyclerView.Adapter<CustomAdapterFriend.ViewHolder> {
    List<Friend> mFriends;
    OnClckFriend mOnClckFriend;

    public CustomAdapterFriend(List<Friend> friends, OnClckFriend onClckFriend) {
        mFriends = friends;
        mOnClckFriend = onClckFriend;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = friend;
        Glide.with(holder.imgItem.getContext()).load(R.drawable.avatar_oneperson).into(holder.imgItem);
//        holder.imgItem.setImageResource(R.drawable.avatar_oneperson);
        holder.tvItemNickname.setText(friend.getNickname());
        holder.tvItemFullname.setText(friend.getFullName());
        holder.btnItemText.setText("Text");
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public void changList(List<Friend> notFriends) {
        mFriends = notFriends;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Friend mFriend;
        int position;
        ImageView imgItem;
        TextView tvItemNickname, tvItemFullname;
        Button btnItemText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvItemNickname = itemView.findViewById(R.id.tvItemNickname);
            tvItemFullname = itemView.findViewById(R.id.tvItemFullname);
            btnItemText = itemView.findViewById(R.id.btnItemText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClckFriend.clickItem(mFriend);
                }
            });
            btnItemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClckFriend.buttonTextClick(mFriend);
                }
            });
        }
    }
}
