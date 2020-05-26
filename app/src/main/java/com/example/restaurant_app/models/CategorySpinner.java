package com.example.restaurant_app.models;

/**
 *
 * @author Jonas Mitschke
 * @content definition of reduced category-class (for spinner)
 */
public class CategorySpinner {
    private String id;
    private String name;

// constructor
    public CategorySpinner(String id, String name) {
        this.id = id;
        this.name = name;
    }

// getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

// setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

// toString
    @Override
    public String toString() {
        return name;
    }
}
