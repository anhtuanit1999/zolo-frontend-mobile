package com.example.zolo_frondend_mobile.danhsach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zolo_frondend_mobile.R;

import java.util.List;

public class CustomAdapterMember extends RecyclerView.Adapter<CustomAdapterMember.ViewHolder> {
    List<Friend> mFriends;
    OnClickMem mOnClickMem;

    public CustomAdapterMember(List<Friend> friends, OnClickMem onClckFriend) {
        mFriends = friends;
        mOnClickMem = onClckFriend;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = friend;
        holder.imgItem.setImageResource(R.drawable.tuong);
        holder.tvItemNickname.setText(friend.getNickname());
        holder.tvItemFullname.setText(friend.getFullName());
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
        CheckBox cbMem;
        ImageView imgItem;
        TextView tvItemNickname, tvItemFullname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgMem);
            tvItemNickname = itemView.findViewById(R.id.tvMemNickName);
            tvItemFullname = itemView.findViewById(R.id.tvMemFullName);
            cbMem = itemView.findViewById(R.id.cbMem);
            cbMem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickMem.CliclItemCheckBox(mFriend, cbMem);
                }
            });
        }
    }
}
