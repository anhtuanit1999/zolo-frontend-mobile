package com.example.zolo_frondend_mobile.danhsach;

import java.io.Serializable;

public class Friend implements Serializable {
    private Integer id;
    private String nickname;
    private String fullName;
    private String email;
    private String phone;

    public Friend(Integer id, String nickname, String fullName, String email, String phone) {
        this.id = id;
        this.nickname = nickname;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public Friend() {
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
