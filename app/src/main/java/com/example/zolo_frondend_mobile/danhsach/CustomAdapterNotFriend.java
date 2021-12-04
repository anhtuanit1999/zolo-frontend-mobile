package com.example.zolo_frondend_mobile.danhsach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolo_frondend_mobile.R;

import java.util.List;

public class CustomAdapterNotFriend extends RecyclerView.Adapter<CustomAdapterNotFriend.ViewHolder>{
    List<Friend> mFriends;
    OnClickNotFriend mOnClickNotFriend;

    public CustomAdapterNotFriend(List<Friend> friends, OnClickNotFriend onClickNotFriend) {
        mFriends = friends;
        mOnClickNotFriend = onClickNotFriend;
    }

    @NonNull
    @Override
    public CustomAdapterNotFriend.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notfriend,parent,false);
        return new CustomAdapterNotFriend.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterNotFriend.ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = friend;
        holder.imgItem.setImageResource(R.drawable.tuong);
        holder.tvItemNickname.setText(friend.getNickname());
        holder.tvItemFullname.setText(friend.getFullName());
        holder.btnAddFriend.setText("Kết bạn");
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
        Button btnAddFriend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgAItem);
            tvItemNickname = itemView.findViewById(R.id.tvAItemNickname);
            tvItemFullname = itemView.findViewById(R.id.tvAItemFullname);
            btnAddFriend = itemView.findViewById(R.id.btnAccept);

            btnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickNotFriend.ButtonAddFriendClick(mFriend);
                }
            });

        }
    }
}
