package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

/**
 *
 * @author Jonas Mitschke
 * @content definition of table-class
 */
public class Table {
    @DocumentId
    private String tableId;
    private Restaurant restaurant;
    private Integer tableNumber;

// constructors
    public Table() {}

    public Table(Restaurant restaurant, Integer tableNumber) {
        this.restaurant = restaurant;
        this.tableNumber = tableNumber;
    }

// getters
    public String getTableId() {
        return tableId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

// setters
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setTableNumber(Integer no) {
        this.tableNumber = no;
    }
}
