package com.example.zolo_frondend_mobile.entity;

import com.example.zolo_frondend_mobile.model.User;

public class StatusCode200SignIn {
    private Integer code;
    private User data;

    public StatusCode200SignIn(Integer code, User data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
