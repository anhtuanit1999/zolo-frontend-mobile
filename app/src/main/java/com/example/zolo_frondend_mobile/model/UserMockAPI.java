package com.example.zolo_frondend_mobile.model;

public class UserMockAPI {
    private String id;
    private String email;
    private String matkhau;
    private int tuoi;
    private String ten;

    public UserMockAPI(String id, String email, String matkhau, int age, String ten) {
        this.id = id;
        this.email = email;
        this.matkhau = matkhau;
        this.tuoi = age;
        this.ten = ten;
    }

    public UserMockAPI(String email, String matkhau, int age, String ten) {
        this.email = email;
        this.matkhau = matkhau;
        this.tuoi = age;
        this.ten = ten;
    }

    public UserMockAPI() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getAge() {
        return tuoi;
    }

    public void setAge(int age) {
        this.tuoi = age;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "UserMockAPI{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", age=" + tuoi +
                ", ten='" + ten + '\'' +
                '}';
    }
}
