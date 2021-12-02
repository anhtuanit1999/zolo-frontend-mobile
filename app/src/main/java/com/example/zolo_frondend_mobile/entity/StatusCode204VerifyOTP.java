package com.example.zolo_frondend_mobile.entity;

public class StatusCode204VerifyOTP {
    private Integer code;
    private String message;

    public StatusCode204VerifyOTP(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public StatusCode204VerifyOTP() {
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
}
