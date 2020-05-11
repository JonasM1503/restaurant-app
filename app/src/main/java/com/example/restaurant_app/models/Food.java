package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

/**
 *
 * @author Jonas Mitschke
 * @content definition of food-class
 */
public class Food {
    @DocumentId
    private String foodId;
    private Restaurant restaurant;
    private Category category;
    private String name;
    private Double price;
    private String description;
    private String pictureURL;

// constructors
    public Food() {}

    public Food(Restaurant restaurant, Category category, String name, Double price, String description,
                String pictureURL) {
        this.restaurant = restaurant;
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureURL = pictureURL;
    }

// getters
    public String getFoodId() {
        return foodId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Category getCategory() {
        return category;
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
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setCategory(Category category) {
        this.category = category;
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
