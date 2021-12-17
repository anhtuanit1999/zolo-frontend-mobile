package com.example.zolo_frondend_mobile.chat;

public class MessageSend {
    private Integer id;
    private Integer chatgroupId;
    private Integer userId;
    private String mess;
    private Long createAt;
    private Integer statusId;
    private String user;
    private String type;

    public MessageSend(Integer id, Integer chatgroupId, Integer userId, String mess, Long createAt, Integer statusId, String user, String type) {
        this.id = id;
        this.chatgroupId = chatgroupId;
        this.userId = userId;
        this.mess = mess;
        this.createAt = createAt;
        this.statusId = statusId;
        this.user = user;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageSend{" +
                "id=" + id +
                ", chatgroupId=" + chatgroupId +
                ", userId=" + userId +
                ", mess='" + mess + '\'' +
                ", createAt=" + createAt +
                ", statusId=" + statusId +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public MessageSend() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatgroupId() {
        return chatgroupId;
    }

    public void setChatgroupId(Integer chatgroupId) {
        this.chatgroupId = chatgroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
