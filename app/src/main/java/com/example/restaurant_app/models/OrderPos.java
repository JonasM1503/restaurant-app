package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of orderPos-class
 */
public class OrderPos {
    private String orderPosUUID;
    private String foodId;
    private String drinkId;
    private String wish;
    private int quantity;


// constructors
    public OrderPos() {}

    public OrderPos(Food food, String wish, int quantity) {
        this.orderPosUUID = UUID.randomUUID().toString();
        this.foodId = food.getFoodId();
        this.wish = wish;
        this.quantity = quantity;
    }

    public OrderPos(Drink drink, String wish, int quantity) {
        this.orderPosUUID = UUID.randomUUID().toString();
        this.drinkId = drink.getDrinkId();
        this.wish = wish;
        this.quantity = quantity;
    }

// getters
    public String getOrderPosUUID() {
        return orderPosUUID;
    }

    public String getFoodId() { return this.foodId; }

    public String getDrinkId() { return this.drinkId; }

    public String getWish() {
        return wish;
    }

    public int getQuantity() {
        return quantity;
    }

    // setters
    public void setFoodId(String foodId) {
        if (this.drinkId != null) {
            this.drinkId = null;
        }
        this.foodId = foodId;
    }

    public void setDrinkId(String drinkId) {
        if (this.foodId != null) {
            this.foodId = null;
        }
        this.drinkId = drinkId;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
