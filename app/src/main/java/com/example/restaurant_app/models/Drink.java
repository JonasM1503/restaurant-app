package com.example.restaurant_app.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 *
 * @author Simon Rothmann
 * @content definition of drink-class
 */
public class Drink {

    private String drinkId;
    private String restaurantId;
    private String categoryId;
    private String name;
    private Double price;
    private String description;
    private String pictureUrl;

    //constructor
    public Drink(){}

    public Drink(String restaurantId, String categoryId, String name, Double price,
                 String description, String pictureUrl) {
        this.drinkId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }

    //getter
    public String getDrinkId() { return drinkId; }

    public String getRestaurantId() { return restaurantId; }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() { return name; }

    public Double getPrice() { return price; }

    public String getDescription() { return description; }

    public String getPictureUrl() { return pictureUrl; }

    //setter
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) { this.name = name; }

    public void setPrice(Double price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

}

