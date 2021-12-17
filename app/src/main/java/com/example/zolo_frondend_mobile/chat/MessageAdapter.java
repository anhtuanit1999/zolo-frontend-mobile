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
import com.example.zolo_frondend_mobile.utils.JWTUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    List<MessageGet> mChats;
    OnClickAddFriend mAddFriend;
    private String imageurl;
    private Integer position1;

    public MessageAdapter(List<MessageGet> friends) {
        mChats = friends;
//        mAddFriend = onClickNotFriend;
//        imageurl=  imageurl1;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_tem_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        MessageGet chat = mChats.get(position);
        holder.show_message.setText(chat.getContent());
        holder.tv_Name_text.setText(chat.getUser());


        Date date = new Date(chat.getCreateAt());
//        String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String formatted = new SimpleDateFormat("HH:mm").format(date);
        holder.tv_time.setText(formatted+"");

//        if(imageurl.equals("default"){
//            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
//        }else{
//            GLode.with(mContext).load(imageurl).into(holder.profile_image);
//        }

    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }
    public void changList(List<MessageGet> notFriends) {
        mChats = notFriends;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chat mChat;
        int position;
        ImageView profile_image;
        TextView show_message, tv_Name_text, tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image =  itemView.findViewById(R.id.profile_image);
            show_message =  itemView.findViewById(R.id.show_message);
            tv_Name_text =  itemView.findViewById(R.id.tv_Name_text);
            tv_time =  itemView.findViewById(R.id.tv_time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Integer id = mChats.get(position).getUserId();
        Integer userId = JWTUtils.USER_ZOLO.getId();
        if( String.valueOf(id).equals(String.valueOf(userId))){
            return MSG_TYPE_RIGHT;//1
        }else{
            return MSG_TYPE_LEFT;//0
        }
    }
}
