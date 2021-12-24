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

public class CustomAdapterGroupMember extends RecyclerView.Adapter<CustomAdapterGroupMember.ViewHolder> {
    List<Friend> mFriends;
    OnClickMemberGroup mOnClickMemberGroup;

    public CustomAdapterGroupMember(List<Friend> friends, OnClickMemberGroup onClckFriend) {
        mFriends = friends;
        mOnClickMemberGroup = onClckFriend;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_group_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.mFriend = mFriends.get(position);
        holder.imgItem.setImageResource(R.drawable.avatar_oneperson);
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
        ImageView imgItem;
        TextView tvItemNickname, tvItemFullname;
        Button btnPGFKick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgPGF);
            tvItemNickname = itemView.findViewById(R.id.tvPGFNckName);
            tvItemFullname = itemView.findViewById(R.id.tvPGFFullName);
            btnPGFKick = itemView.findViewById(R.id.btnPGFKick);
            btnPGFKick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickMemberGroup.ClickButton(mFriend);
                }
            });
        }
    }
}
