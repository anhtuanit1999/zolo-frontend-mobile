package com.example.zolo_frondend_mobile.chat;

public class IdSingleGroup {
    private Integer id;

    public IdSingleGroup(Integer id) {
        this.id = id;
    }

    public IdSingleGroup() {
    }

    @Override
    public String toString() {
        return "IdSingleGroup{" +
                "id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
