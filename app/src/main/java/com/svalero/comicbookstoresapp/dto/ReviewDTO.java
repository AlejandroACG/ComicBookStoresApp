package com.svalero.comicbookstoresapp.dto;

public class ReviewDTO {
    private Long userId;
    private Long storeId;
    private String title;
    private String content;

    public ReviewDTO(Long userId, Long storeId, String title, String content) {
        this.userId = userId;
        this.storeId = storeId;
        this.title = title;
        this.content = content;
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getStoreId() { return storeId; }

    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
