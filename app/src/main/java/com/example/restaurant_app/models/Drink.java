package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;
/**
 *
 * @author Simon Rothmann
 * @content definition of drink-class
 */
public class Drink {
    @DocumentId
    private String drinkId;
    private Restaurant restaurant;
    private Category category;
    private String name;
    private Double price;
    private String description;
    private String pictureUrl;

    //constructor
    public Drink(){}

    public Drink(Restaurant restaurantId, Category category, String name, Double price,
                 String description, String pictureUrl) {
        this.restaurant = restaurantId;
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }

    //getter
    public String getDrinkId() { return drinkId; }

    public Restaurant getRestaurant() { return restaurant; }

    public Category getCategory() {
        return category;
    }

    public String getName() { return name; }

    public Double getPrice() { return price; }

    public String getDescription() { return description; }

    public String getPictureUrl() { return pictureUrl; }

    //setter
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) { this.name = name; }

    public void setPrice(Double price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }
}

