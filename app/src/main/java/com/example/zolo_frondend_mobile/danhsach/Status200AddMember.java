package com.example.zolo_frondend_mobile.danhsach;

public class Status200AddMember {
    private Integer code;
    private String message;

    public Status200AddMember(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Status200AddMember() {
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
