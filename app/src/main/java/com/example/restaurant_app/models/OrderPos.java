package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of orderPos-class
 */
public class OrderPos {
    private String orderPosUUID;
    private Food food;
    private Drink drink;
    private String wish;
    private int quantity;


// constructors
    public OrderPos() {}

    public OrderPos(Food food, String wish, int quantity) {
        this.orderPosUUID = UUID.randomUUID().toString();
        this.food = food;
        this.wish = wish;
        this.quantity = quantity;
    }

    public OrderPos(Drink drink, String wish, int quantity) {
        this.orderPosUUID = UUID.randomUUID().toString();
        this.drink = drink;
        this.wish = wish;
        this.quantity = quantity;
    }

// getters
    public String getOrderPosUUID() {
        return orderPosUUID;
    }

    public Food getFood() { return this.food; }

    public Drink getDrink() { return this.drink; }

    public String getWish() {
        return wish;
    }

    public int getQuantity() {
        return quantity;
    }

    // setters
    public void setFood(Food food) {
        if (this.drink != null) {
            this.drink = null;
        }
        this.food = food;
    }

    public void setDrink(Drink drink) {
        if (this.food != null) {
            this.food = null;
        }
        this.drink = drink;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
