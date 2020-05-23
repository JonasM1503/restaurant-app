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
    private String qrCode;

// constructors
    public Table() {}

    public Table(Restaurant restaurant, Integer tableNumber, String qrCode) {
        this.restaurant = restaurant;
        this.tableNumber = tableNumber;
        this.qrCode = qrCode;
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

    public String getQrCode() {
        return qrCode;
    }

// setters
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
