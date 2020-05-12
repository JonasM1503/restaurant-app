package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

/**
 *
 * @author Simon Rothmann
 * @content definition of restaurant-class
 */
public class Restaurant {
    @DocumentId
    private String restaurantId;
    private String restaurantName;
    private Address address;
    private String taxNumber;

    //constructor
    public Restaurant(String restaurantName, Address address, String taxNumber)
    {
        this.restaurantName = restaurantName;
        this.address = address;
        this.taxNumber = taxNumber;
    }

    //getter
    public String getRestaurantId() { return restaurantId; }
    

    public String getRestaurantName() { return restaurantName; }

    public Address getAddress() { return address; }

    public String getTaxNumber() {  return taxNumber; }

    //setter

    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public void setAddress(Address address) { this.address = address; }

    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
}
