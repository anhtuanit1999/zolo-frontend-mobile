package com.example.zolo_frondend_mobile.chat;

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
import com.example.zolo_frondend_mobile.danhsach.OnClickAddFriend;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    List<Friend> mFriends;
    OnClickAddFriend mAddFriend;

    public MessageAdapter(List<Friend> friends, OnClickAddFriend onClickNotFriend) {
        mFriends = friends;
        mAddFriend = onClickNotFriend;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept,parent,false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = friend;
        holder.imgAItem.setImageResource(R.drawable.tuong);
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
