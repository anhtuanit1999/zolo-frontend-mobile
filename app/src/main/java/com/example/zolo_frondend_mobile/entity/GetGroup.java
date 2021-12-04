package com.example.zolo_frondend_mobile.entity;

import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.group.Group;

import java.util.List;

public class GetGroup {
    private int code;
    private List<Group> data;

    public GetGroup(int code, List<Group> data) {
        this.code = code;
        this.data = data;
    }

    public GetGroup() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }
}
