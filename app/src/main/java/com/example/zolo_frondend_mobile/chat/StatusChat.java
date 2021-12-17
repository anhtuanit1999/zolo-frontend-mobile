package com.example.zolo_frondend_mobile.chat;

public class StatusChat {
    private Integer code;
    private String data;

    public StatusChat(Integer code, String data) {
        this.code = code;
        this.data = data;
    }

    public StatusChat() {
    }

    @Override
    public String toString() {
        return "StatusChat{" +
                "code=" + code +
                ", data='" + data + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
