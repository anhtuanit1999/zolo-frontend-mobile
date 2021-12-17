package com.example.zolo_frondend_mobile.chat;

import java.util.List;

public class StatusMessageGet {
    private Integer code;
    private List<MessageGet> data;

    public StatusMessageGet(Integer code, List<MessageGet> data) {
        this.code = code;
        this.data = data;
    }

    public StatusMessageGet() {
    }

    @Override
    public String toString() {
        return "StatusMessageGet{" +
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

    public List<MessageGet> getData() {
        return data;
    }

    public void setData(List<MessageGet> data) {
        this.data = data;
    }
}
