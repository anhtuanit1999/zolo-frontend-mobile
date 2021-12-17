package com.example.zolo_frondend_mobile.chat;

public class MessageGet{
    private Integer id;
    private Integer chatgroupId;
    private Integer userId;
    private String content;
    private Long createAt;
    private Integer statusId;
    private String user;
    private String type;

    public MessageGet(Integer id, Integer chatgroupId, Integer userId, String content, Long createAt, Integer statusId, String user, String type) {
        this.id = id;
        this.chatgroupId = chatgroupId;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
        this.statusId = statusId;
        this.user = user;
        this.type = type;
    }

    public MessageGet() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "MessageGet{" +
                "id=" + id +
                ", chatgroupId=" + chatgroupId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createAt=" + createAt +
                ", statusId=" + statusId +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
