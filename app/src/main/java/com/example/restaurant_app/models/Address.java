package com.example.restaurant_app.models;

import java.util.UUID;

/**
 *
 * @author Simon Rothmann
 * @content definition of address-class
 */
public class Address {
    private String addressId;
    private String addressStreet;
    private String addressZipCode;
    private String addressCity;
    private String addressCountry;

//constructors
    public Address() {
    }

    public Address(String addressStreet, String addressZipCode,
                   String addressCity, String addressCountry) {
        this.addressId = UUID.randomUUID().toString();
        this.addressStreet = addressStreet;
        this.addressZipCode = addressZipCode;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
    }


//getter
    public String getAddressId() { return addressId; }

    public String getAddressStreet() { return addressStreet; }

    public String getAddressZipCode() { return addressZipCode; }

    public String getAddressCity() { return addressCity; }

    public String getAddressCountry() { return addressCountry; }

//setter
    public void setAddressId(String addressId) { this.addressId = addressId; }

    public void setAddressStreet(String addressStreet) { this.addressStreet = addressStreet; }

    public void setAddressZipCode(String addressZipCode) { this.addressZipCode = addressZipCode; }

    public void setAddressCity(String addressCity) { this.addressCity = addressCity; }

    public void setAddressCountry(String addressCountry) { this.addressCountry = addressCountry; }
}
