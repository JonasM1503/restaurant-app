package com.example.restaurant_app.models;

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

// constructors
    public Order() {}

    public Order( String tableId, Boolean paid) {
        this.orderId = UUID.randomUUID().toString();
        this.tableId = tableId;
        this.paid = paid;

    }

// getters
    public String getOrderId() {
        return this.orderId;
    }

    public String getTable() {
        return this.tableId;
    }

    public Boolean getPaid() { return this.paid; }


// setters

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

}
