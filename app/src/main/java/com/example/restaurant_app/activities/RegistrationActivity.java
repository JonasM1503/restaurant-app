package com.example.restaurant_app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.firestore.AddressFirestoreManager;
import com.example.restaurant_app.firestore.RestaurantFirestoreManager;
import com.example.restaurant_app.firestore.UserFirestoreManager;
import com.example.restaurant_app.models.Address;
import com.example.restaurant_app.models.Restaurant;
import com.example.restaurant_app.models.User;

public class RegistrationActivity extends AppCompatActivity {
    private RestaurantFirestoreManager resManager;
    private UserFirestoreManager userManager;
    private AddressFirestoreManager addressManager;

    /**
     *
     * @author Simon Rothmann
     * @content registration prozess
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        resManager = RestaurantFirestoreManager.newInstance();
        userManager = UserFirestoreManager.newInstance();
        addressManager = AddressFirestoreManager.newInstance();

        final Button restigsterButton = findViewById(R.id.finishRegistration);
        final String firstName = findViewById(R.id.registerFirstName).toString();
        final String lastName = findViewById(R.id.registerLastName).toString();
        final String email = findViewById(R.id.registerEmail).toString();
        final String password = findViewById(R.id.registerPassword).toString();
        final String street = findViewById(R.id.registerStreet).toString();
        final String zipCode = findViewById(R.id.registerZipCode).toString();
        final String city = findViewById(R.id.registerCity).toString();
        final String restaurantName = findViewById(R.id.registerRestaurantName).toString();
        final String ustId = findViewById(R.id.registerUstId).toString();


        restigsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address =  new Address(street, zipCode, city, "Germany");
                Restaurant restaurant = new Restaurant(restaurantName, address, ustId);
                User user = new User(email, password, firstName, lastName, address, false, restaurant);

                resManager.createRestaurant(restaurant);
                addressManager.createAddress(address);
                userManager.createUser(user);
                setContentView(R.layout.activity_main);
            }
        });
    }

}
