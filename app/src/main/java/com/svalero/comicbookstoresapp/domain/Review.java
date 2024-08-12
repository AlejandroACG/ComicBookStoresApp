package com.svalero.comicbookstoresapp.domain;

public class Review {
    private Long id;
    private String title;
    private String content;
    private Float rating;
    private String date;

    public Review(Long id, String title, String content, float rating, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.date = date;
    }

    public Long getId() {
        return id;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
