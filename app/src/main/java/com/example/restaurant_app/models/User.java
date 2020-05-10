package com.example.restaurant_app.models;

import com.google.firebase.firestore.DocumentId;

public class User {
    @DocumentId
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String addressId;
    private Boolean isAdmin;

    //constructor
    public User(String userId, String email, String password, String firstName, String lastName,
                String addressId, Boolean isAdmin) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.isAdmin = isAdmin;
    }

    //getter
    public String getUserId() { return userId; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getAddressId() { return addressId; }

    public Boolean getAdmin() { return isAdmin;  }

    //setter
    public void setUserId(String userId) { this.userId = userId; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setAddressId(String addressId) { this.addressId = addressId; }

    public void setAdmin(Boolean admin) { isAdmin = admin; }
}
