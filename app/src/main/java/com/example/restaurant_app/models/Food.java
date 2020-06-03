package com.example.restaurant_app.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
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
    private String pictureUrl;

// constructors
    public Food() {}

    public Food(String restaurantId, String categoryId, String name, Double price, String description,
                String pictureUrl) {
        this.foodId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
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

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }


}

