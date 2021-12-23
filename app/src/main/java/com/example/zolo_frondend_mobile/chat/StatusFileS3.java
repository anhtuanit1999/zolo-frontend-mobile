package com.example.zolo_frondend_mobile.chat;

public class StatusFileS3 {
    private Integer code;
    private String message;
    private fileName data;

    public StatusFileS3(Integer code, String message, fileName data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public fileName getData() {
        return data;
    }

    public void setData(fileName data) {
        this.data = data;
    }
}
