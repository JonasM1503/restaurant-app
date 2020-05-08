package com.example.restaurant_app.models;

public class Restaurant {
    private String restaurantId;
    private String userId;
    private String restaurantName;
    private String addressId;
    private String taxNumber;

    //constructor
    public Restaurant(String restaurantId, String userId, String restaurantName,
                      String addressId, String taxNumber)
    {
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.addressId = addressId;
        this.taxNumber = taxNumber;
    }

    //getter
    public String getRestaurantId() { return restaurantId; }

    public String getUserId() { return userId; }

    public String getRestaurantName() { return restaurantName; }

    public String getAddressId() { return addressId; }

    public String getTaxNumber() {  return taxNumber; }

    //setter

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public void setAddressId(String addressStreet) { this.addressId = addressStreet; }

    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
}
