package com.svalero.comicbookstoresapp.domain;

public class Review {
    private Long id;
    private Long storeId;
    private Long userId;
    private String title;
    private String content;
    private String date;

    public Review(Long id, Long storeId, Long userId, String title, String content, String date) {
        this.id = id;
        this.storeId = storeId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
