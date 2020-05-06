package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class Food {
    @DocumentId
    private String foodId;
    private String restaurant;      // change to Restaurant-object once available
    private String name;
    private Double price;
    private String description;
    private String pictureURL;

// constructors
    public Food() {}

    public Food(String restaurant, String name, Double price, String description, String pictureURL) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureURL = pictureURL;
    }

// getters
    public String getFoodId() {
        return foodId;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureURL() {
        return pictureURL;
    }

// setters
    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
