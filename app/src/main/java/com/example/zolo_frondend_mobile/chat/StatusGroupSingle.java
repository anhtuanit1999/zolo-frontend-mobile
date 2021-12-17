package com.example.zolo_frondend_mobile.chat;

public class StatusGroupSingle {
    private Integer code;
    private IdSingleGroup data;

    public StatusGroupSingle(Integer code, IdSingleGroup data) {
        this.code = code;
        this.data = data;
    }

    public StatusGroupSingle() {
    }

    @Override
    public String toString() {
        return "StatusGroupSingle{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public IdSingleGroup getData() {
        return data;
    }

    public void setData(IdSingleGroup data) {
        this.data = data;
    }
}
