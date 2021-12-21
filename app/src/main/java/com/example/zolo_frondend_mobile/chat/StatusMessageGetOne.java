package com.example.zolo_frondend_mobile.chat;

import java.util.List;

public class StatusMessageGetOne {
    private Integer code;
    private MessageGet data;

    public StatusMessageGetOne(Integer code, MessageGet data) {
        this.code = code;
        this.data = data;
    }

    public StatusMessageGetOne() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MessageGet getData() {
        return data;
    }

    public void setData(MessageGet data) {
        this.data = data;
    }
}
