package com.example.zolo_frondend_mobile.chat;

import com.example.zolo_frondend_mobile.group.Group;

public class StatusGetOneGroup {
    private Integer code;
    private Group data;

    public StatusGetOneGroup(Integer code, Group data) {
        this.code = code;
        this.data = data;
    }

    public StatusGetOneGroup() {
    }

    @Override
    public String toString() {
        return "StatusGetOneGroup{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Group getData() {
        return data;
    }

    public void setData(Group data) {
        this.data = data;
    }
}
