package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of category-class
 */
public class Category {
    private String categoryId;
    private String restaurantId;
    private String name;

// constructors
    public Category() {}

    public Category(String restaurantId, String name) {
        this.categoryId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.name = name;
    }

// getters
    public String getCategoryId() {
        return categoryId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

// setters
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
