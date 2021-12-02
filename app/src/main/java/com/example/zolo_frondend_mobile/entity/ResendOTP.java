package com.example.zolo_frondend_mobile.entity;

public class ResendOTP {
    private String email;

    public ResendOTP(String email) {
        this.email = email;
    }

    public ResendOTP() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
