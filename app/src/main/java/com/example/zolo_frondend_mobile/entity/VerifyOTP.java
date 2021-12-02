package com.example.zolo_frondend_mobile.entity;

public class VerifyOTP {
    private String email;
    private String otp;

    public VerifyOTP(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
