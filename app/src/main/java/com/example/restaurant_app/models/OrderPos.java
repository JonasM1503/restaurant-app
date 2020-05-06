package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class OrderPos {
    @DocumentId
    private String orderPosId;
    private Order order;
    private Food food;
    private Drink drink;


// constructors
    public OrderPos() {}

    public OrderPos(Order order, Food food) {
        this.order = order;
        this.food = food;
    }

    public OrderPos(Order order, Drink drink) {
        this.order = order;
        this.drink = drink;
    }

// getters
    public String getOrderPosId() {
        return orderPosId;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    public Drink getDrink() {
        return drink;
    }

// setters
    public void setOrderPosId(String orderPosId) {
        this.orderPosId = orderPosId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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
}
