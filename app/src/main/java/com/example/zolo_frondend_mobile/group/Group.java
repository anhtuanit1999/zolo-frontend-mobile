package com.example.zolo_frondend_mobile.group;

import java.io.Serializable;

public class Group implements Serializable {
    private int id;
    private String name;
    private long create_at;
    private int status_id;
    private int create_by;

    public Group(int id, String name, long create_at, int status_id, int create_by) {
        this.id = id;
        this.name = name;
        this.create_at = create_at;
        this.status_id = status_id;
        this.create_by = create_by;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", create_at=" + create_at +
                ", status_id=" + status_id +
                ", create_by=" + create_by +
                '}';
    }

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreate_at() {
        return create_at;
    }

    public void setCreate_at(long create_at) {
        this.create_at = create_at;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }
}
