package com.example.zolo_frondend_mobile;

import android.widget.Button;

import java.io.Serializable;

public class Ketban implements Serializable {
    private String tvNameKetBan;
    private int btnKetBan;
    private int img_KetBan;

    public Ketban(String tvNameKetBan, int btnKetBan, int img_KetBan) {
        this.tvNameKetBan = tvNameKetBan;
        this.btnKetBan = btnKetBan;
        this.img_KetBan = img_KetBan;
    }

    public String getTvNameKetBan() {
        return tvNameKetBan;
    }

    public void setTvNameKetBan(String tvNameKetBan) {
        this.tvNameKetBan = tvNameKetBan;
    }

    public int getBtnKetBan() {
        return btnKetBan;
    }

    public void setBtnKetBan(int btnKetBan) {
        this.btnKetBan = btnKetBan;
    }

    public int getImg_KetBan() {
        return img_KetBan;
    }

    public void setImg_KetBan(int img_KetBan) {
        this.img_KetBan = img_KetBan;
    }
}
