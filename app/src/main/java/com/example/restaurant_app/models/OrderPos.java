package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of orderIdPos-class
 */
public class OrderPos {
    private String orderPosId;
    private String orderId;
    private String foodId;
    private String drinkId;


// constructors
    public OrderPos() {}

    public OrderPos(String orderId, Food food) {
        this.orderPosId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.foodId = food.getFoodId();
    }

    public OrderPos(String orderId, Drink drink) {
        this.orderPosId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.drinkId = drink.getDrinkId();
    }

// getters
    public String getOrderPosId() { return this.orderPosId; }

    public String getOrderId() { return this.orderId; }

    public String getFoodId() { return this.foodId; }

    public String getDrinkId() { return this.drinkId; }

// setters
    public void setOrderId(String orderId) { this.orderId = orderId; }

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
}
