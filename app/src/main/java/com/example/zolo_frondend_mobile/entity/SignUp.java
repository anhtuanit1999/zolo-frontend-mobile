package com.example.zolo_frondend_mobile.entity;

public class SignUp {
    private String phone;
    private String nickname;
    private String fullName;
    private String email;
    private String password;
    private int idRole;



    public SignUp(String phone, String nickname, String fullname, String email, String password, int idRole) {
        this.phone = phone;
        this.nickname = nickname;
        this.fullName = fullname;
        this.email = email;
        this.password = password;
        this.idRole = 1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", fullname='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", idRole=" + idRole +
                '}';
    }
}
