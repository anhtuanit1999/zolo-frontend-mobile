package com.example.zolo_frondend_mobile.chat;

import android.net.Uri;
import android.util.Log;
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
    OnClickMessage mOnClickMessage;
    private String imageurl;
    private Integer position1;

    public MessageAdapter(List<MessageGet> chats, OnClickMessage onClickMessage) {
        mChats = chats;
        mOnClickMessage = onClickMessage;
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
        holder.img_message.setVisibility(View.GONE);
        holder.tv_Name_text.setText(chat.getUser());
        Date date = new Date(chat.getCreateAt());
//        String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String formatted = new SimpleDateFormat("HH:mm").format(date);
        holder.tv_time.setText(formatted+"");
        String strUrl = chat.getContent();
        if(strUrl.contains(".jpg") || strUrl.contains(".jpeg") || strUrl.contains(".png") ||
                strUrl.contains(".JPG") || strUrl.contains(".JPEG") || strUrl.contains(".PNG")){
            holder.show_message.setVisibility(View.GONE);
            Uri myUri = Uri.parse(JWTUtils.PATH_S3+strUrl);
            Log.e("S3",strUrl);
            holder.img_message.setVisibility(View.VISIBLE);
//            holder.img_message.setImageURI(myUri);
            Glide.with(holder.img_message.getContext()).load(myUri).into(holder.img_message);
        }else{
            holder.img_message.setVisibility(View.GONE);
            holder.show_message.setVisibility(View.VISIBLE);
            holder.show_message.setText(chat.getContent());
        }
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
        ImageView profile_image, img_message;
        TextView show_message, tv_Name_text, tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image =  itemView.findViewById(R.id.profile_image);
            show_message =  itemView.findViewById(R.id.show_message);
            tv_Name_text =  itemView.findViewById(R.id.tv_Name_text);
            img_message =  itemView.findViewById(R.id.img_message);
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
