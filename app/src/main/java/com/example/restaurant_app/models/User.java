package com.example.restaurant_app.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Simon Rothmann, Jonas Mitschke
 * @content definition of user-class
 */
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Restaurant restaurant;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

//constructors
    public User() {
    }

    public User(String email, String password, String firstName, String lastName,
                Boolean isActive, Restaurant restaurant) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.restaurant = restaurant;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

//getters
    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Boolean getIsActive() { return isActive;  }

    public Restaurant getRestaurant() { return restaurant; }

//setter
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }


/**
*
* @author   Jonas Mitschke
* @content  check whether user is a admin
*/
    public boolean checkWhetherAdmin(){
        return this.getRestaurant() == null;
    }

/**
*
* @author   Jonas Mitschke
* @content  encrypt password
* @param    password   plain text password, which needs to be encrypted
* @return   encrypted password
*/
    public static String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }

        // Hashwert zum Passwort berechnen
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        // Hashwert in einen Hex-String umwandeln
        char[] hashHex = new char[hash.length * 2];

        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            hashHex[i * 2] = HEX_ARRAY[v >>> 4];
            hashHex[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hashHex);
    }
}
