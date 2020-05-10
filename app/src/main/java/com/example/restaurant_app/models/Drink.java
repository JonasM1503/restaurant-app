package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class Drink {
    @DocumentId
    private String drinkId;
    private String restaurantId;
    private String name;
    private Double price;
    private String description;
    private String pictureUrl;

    //constructor
    public Drink(String drinkId, String restaurantId, String name, Double price,
                 String description, String pictureUrl) {
        this.drinkId = drinkId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
    }

    //getter
    public String getDrinkId() { return drinkId; }

    public String getRestaurantId() { return restaurantId; }

    public String getName() { return name; }

    public Double getPrice() { return price; }

    public String getDescription() { return description; }

    public String getPictureUrl() { return pictureUrl; }

    //setter

    public void setDrinkId(String drinkId) { this.drinkId = drinkId; }

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public void setName(String name) { this.name = name; }

    public void setPrice(Double price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }
}

