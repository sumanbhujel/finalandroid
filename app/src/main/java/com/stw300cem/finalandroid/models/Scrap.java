package com.stw300cem.finalandroid.models;

public class Scrap {

    private String _id;
    private String image;
    private String productType;
    private String description;
    private String location;
    private String userId;

    public Scrap(String image, String productType, String description, String location) {
        this.image = image;
        this.productType = productType;
        this.description = description;
        this.location = location;
    }


    public Scrap(String image, String productType, String description, String location, String userId) {

        this.image = image;
        this.productType = productType;
        this.description = description;
        this.location = location;
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public String getImage() {
        return image;
    }

    public String getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }


    public String getUserId() {
        return userId;
    }
}

