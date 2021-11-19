package com.example.zolo_frondend_mobile.model;

public class User {
    private Integer id;
    private Integer idRole;
    private String nickname;
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private String birthday;
    private String otp;
    private Integer statusId;
    private Long expireOtp;
    private String jwt;

    public User(Integer id, Integer idRole, String nickname, String fullName, String phone, String email, String password, String birthday, String otp, Integer statusId, Long expireOtp, String jwt) {
        this.id = id;
        this.idRole = idRole;
        this.nickname = nickname;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.otp = otp;
        this.statusId = statusId;
        this.expireOtp = expireOtp;
        this.jwt = jwt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Long getExpireOtp() {
        return expireOtp;
    }

    public void setExpireOtp(Long expireOtp) {
        this.expireOtp = expireOtp;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", idRole=" + idRole +
                ", nickname='" + nickname + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", otp='" + otp + '\'' +
                ", statusId=" + statusId +
                ", expireOtp=" + expireOtp +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
