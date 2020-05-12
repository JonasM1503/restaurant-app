package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;
/**
 *
 * @author Simon Rothmann
 * @content definition of user-class
 */
public class User {
    @DocumentId
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Address address;
    private Boolean isActive;
    private Restaurant restaurant;

    //constructor
    public User(String email, String password, String firstName, String lastName,
                Address address, Boolean isActive, Restaurant restaurant) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.isActive = isActive;
        this.restaurant = restaurant;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    //getter
    public String getUserId() { return userId; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Address getAddress() { return address; }

    public Boolean getAdmin() { return isActive;  }

    public Restaurant getRestaurant() { return restaurant; }

    //setter
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setAddress(Address address) { this.address = address; }

    public void setAdmin(Boolean admin) { isActive = admin; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}
