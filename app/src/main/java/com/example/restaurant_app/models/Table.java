package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class Table {
    @DocumentId
    private String tableId;
    private Restaurant restaurant;
    private String qrCode;

// constructors
    public Table() {}

    public Table(Restaurant restaurant, String qrCode) {
        this.restaurant = restaurant;
        this.qrCode = qrCode;
    }

// getters
    public String getTableId() {
        return tableId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getQrCode() {
        return qrCode;
    }

// setters
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
