package com.svalero.comicbookstoresapp.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private List<Review> storeReviews = new ArrayList<>();
    private String username;
    private String email;
    private String password;
    private Float latitude;
    private Float longitude;

    public User(Long id, List<Review> storeReviews, String username, String email, String password, Float latitude, Float longitude) {
        this.id = id;
        this.storeReviews = storeReviews;
        this.username = username;
        this.email = email;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Review> getStoreReviews() {
        return storeReviews;
    }

    public void setStoreReviews(List<Review> storeReviews) {
        this.storeReviews = storeReviews;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
