package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of food-class
 */
public class Food {
    private String foodId;
    private String restaurantId;
    private String categoryId;
    private String name;
    private Double price;
    private String description;
    private String pictureURL;

// constructors
    public Food() {}

    public Food(String restaurantId, String categoryId, String name, Double price, String description,
                String pictureURL) {
        this.foodId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureURL = pictureURL;
    }

// getters
    public String getFoodId() {
        return foodId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getCategoryId() {
        return categoryId;
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
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
