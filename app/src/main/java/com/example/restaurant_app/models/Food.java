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
    private String picture;

// constructors
    public Food() {}

    public Food(String restaurantId, String categoryId, String name, Double price, String description,
                String picture) {
        this.foodId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
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

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String pictureToString(Bitmap picture) {
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String pictureString = Base64.encodeToString(b, Base64.DEFAULT);
        return pictureString;
    }
    public Bitmap StringToPicture(String picture) {
        try {
            byte [] encodeByte=Base64.decode(picture,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}

