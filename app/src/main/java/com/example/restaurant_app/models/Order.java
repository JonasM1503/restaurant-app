package com.example.restaurant_app.models;

import java.util.List;
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
    private List<OrderPos> orderPosList;

// constructors
    public Order() {}

    public Order(String tableId, Boolean paid, List<OrderPos> orderPosList) {
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

    public List<OrderPos> getOrderPosList() {
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

    public void setOrderPosList(List<OrderPos> orderPosList) {
        this.orderPosList = orderPosList;
    }
}
