package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class Order {
    @DocumentId
    private String orderId;
    private Restaurant restaurant;
    private Table table;
    private Boolean paid;

// constructors
    public Order() {}

    public Order(Restaurant restaurant, Table table, Boolean paid) {
        this.restaurant = restaurant;
        this.table = table;
        this.paid = paid;
    }

// getters
    public String getOrderId() {
        return orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Table getTable() {
        return table;
    }

    public Boolean getPaid() {
        return paid;
    }

// setters
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
