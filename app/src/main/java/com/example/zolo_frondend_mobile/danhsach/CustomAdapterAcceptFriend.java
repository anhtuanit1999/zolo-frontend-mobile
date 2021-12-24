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
import com.example.zolo_frondend_mobile.R;

import java.util.List;

public class CustomAdapterAcceptFriend extends RecyclerView.Adapter<CustomAdapterAcceptFriend.ViewHolder>{
    List<Friend> mFriends;
    OnClickAddFriend mAddFriend;

    public CustomAdapterAcceptFriend(List<Friend> friends, OnClickAddFriend onClickNotFriend) {
        mFriends = friends;
        mAddFriend = onClickNotFriend;
    }

    @NonNull
    @Override
    public CustomAdapterAcceptFriend.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept,parent,false);
        return new CustomAdapterAcceptFriend.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterAcceptFriend.ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = friend;
//        holder.imgAItem.setImageResource(R.drawable.tuong);
        Glide.with(holder.imgAItem.getContext()).load(R.drawable.avatar_oneperson).into(holder.imgAItem);
        holder.tvAItemNickname.setText(friend.getNickname());
        holder.tvAItemFullname.setText(friend.getFullName());
        holder.btnAccept.setText("Chấp nhận");
        holder.btnDeny.setText("Hủy");
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
        ImageView imgAItem;
        TextView tvAItemNickname, tvAItemFullname;
        Button btnAccept, btnDeny;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAItem = itemView.findViewById(R.id.imgAItem);
            tvAItemNickname = itemView.findViewById(R.id.tvAItemNickname);
            tvAItemFullname = itemView.findViewById(R.id.tvAItemFullname);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDeny = itemView.findViewById(R.id.btnDeny);

            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddFriend.ButtonAcceptFriendClick(mFriend);
                }
            });
            btnDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddFriend.ButtonDenyFriendClick(mFriend);
                }
            });

        }
    }
}
