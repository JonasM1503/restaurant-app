package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

/**
 *
 * @author Jonas Mitschke
 * @content definition of category-class
 */
public class Category {
    @DocumentId
    private String categoryId;
    private Restaurant restaurant;
    private String name;

// constructors
    public Category() {}

    public Category(Restaurant restaurant, String name) {
        this.restaurant = restaurant;
        this.name = name;
    }

// getters
    public String getCategoryId() {
        return categoryId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

// setters
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setName(String name) {
        this.name = name;
    }
}
