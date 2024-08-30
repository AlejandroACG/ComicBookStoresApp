package com.svalero.comicbookstoresapp.domain;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private Long id;
    private List<Review> storeReviews = new ArrayList<>();
    private String name;
    private String address;
    private Float latitude;
    private Float longitude;
    private String phone;
    private String email;
    private String website;

    public Store(Long id, List<Review> storeReviews, String name, String address, Float latitude, Float longitude,
                 String phone, String email, String website) {
        this.id = id;
        this.storeReviews = storeReviews;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.email = email;
        this.website = website;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
