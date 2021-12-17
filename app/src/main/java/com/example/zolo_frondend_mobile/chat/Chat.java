package com.example.zolo_frondend_mobile.chat;

public class Chat {
    private Integer id;
    private Integer chatgroupId;
    private Integer userId;
    private String content;
    private Long createAt;

    public Chat(Integer id, Integer chatgroupId, Integer userId, String content, Long createAt) {
        this.id = id;
        this.chatgroupId = chatgroupId;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
    }

    public Chat() {
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

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatgroupId=" + chatgroupId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
