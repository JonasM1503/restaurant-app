package com.example.restaurant_app.models;



import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of table-class
 */
public class Table {
    private String tableId;
    private String restaurantId;
    private Integer tableNumber;

// constructors
    public Table() {}

    public Table(String restaurantId, Integer tableNumber) {
        this.tableId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.tableNumber = tableNumber;
    }

// getters
    public String getTableId() {
        return tableId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

// setters
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setTableNumber(Integer no) {
        this.tableNumber = no;
    }
}
