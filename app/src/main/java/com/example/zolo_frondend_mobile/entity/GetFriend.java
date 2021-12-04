package com.example.zolo_frondend_mobile.entity;

import com.example.zolo_frondend_mobile.danhsach.Friend;

import java.util.List;

public class GetFriend {
    private int code;
    private List<Friend> data;

    public GetFriend(int code, List<Friend> data) {
        this.code = code;
        this.data = data;
    }

    public GetFriend() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Friend> getData() {
        return data;
    }

    public void setData(List<Friend> data) {
        this.data = data;
    }
}
