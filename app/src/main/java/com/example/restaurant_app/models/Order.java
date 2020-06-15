package com.example.restaurant_app.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Jonas Mitschke
 * @content definition of order-class
 */
public class Order {
    private String orderId;
    private String tableId;
    private Boolean paid;
    private ArrayList<OrderPos> orderPosList;

// constructors
    public Order() {}

    public Order(String tableId, Boolean paid, ArrayList<OrderPos> orderPosList) {
        this.orderId = UUID.randomUUID().toString();
        this.tableId = tableId;
        this.paid = paid;
        this.orderPosList = orderPosList;
    }

// getters
    public String getOrderId() {
        return this.orderId;
    }

    public String getTableId() {
        return this.tableId;
    }

    public Boolean getPaid() { return this.paid; }

    public ArrayList<OrderPos> getOrderPosList() {
        return orderPosList;
    }

    // setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public void setOrderPosList(ArrayList<OrderPos> orderPosList) {
        this.orderPosList = orderPosList;
    }
}
