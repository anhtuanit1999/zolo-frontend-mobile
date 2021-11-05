package com.example.zolo_frondend_mobile;

import android.widget.TextView;

import java.io.Serializable;

public class XemDSBB implements Serializable {
    private String tvnamebanbe;
    private int img_BanBe;

    public XemDSBB(String tvnamebanbe, int img_BanBe) {
        this.tvnamebanbe = tvnamebanbe;
        this.img_BanBe = img_BanBe;
    }

    public String getTvnamebanbe() {
        return tvnamebanbe;
    }

    public void setTvnamebanbe(String tvnamebanbe) {
        this.tvnamebanbe = tvnamebanbe;
    }

    public int getImg_BanBe() {
        return img_BanBe;
    }

    public void setImg_BanBe(int img_BanBe) {
        this.img_BanBe = img_BanBe;
    }
}
