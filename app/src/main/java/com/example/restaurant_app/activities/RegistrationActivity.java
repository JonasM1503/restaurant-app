package com.example.restaurant_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.firestore.AddressFirestoreManager;
import com.example.restaurant_app.firestore.RestaurantFirestoreManager;
import com.example.restaurant_app.firestore.UserFirestoreManager;
import com.example.restaurant_app.models.Address;
import com.example.restaurant_app.models.Restaurant;
import com.example.restaurant_app.models.User;
/**
 *
 * @author Simon Rothmann
 * @content registration prozess
 */

public class RegistrationActivity extends AppCompatActivity {
    private RestaurantFirestoreManager resManager;
    private UserFirestoreManager userManager;
    private AddressFirestoreManager addressManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        resManager = RestaurantFirestoreManager.newInstance();
        userManager = UserFirestoreManager.newInstance();
        addressManager = AddressFirestoreManager.newInstance();

        final Button registerButton = findViewById(R.id.finishRegistration);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = ((TextView) findViewById(R.id.registerFirstName)).getText().toString();
                final String lastName = ((TextView) findViewById(R.id.registerLastName)).getText().toString();
                final String email = ((TextView) findViewById(R.id.registerEmail)).getText().toString();
                final String password = ((TextView) findViewById(R.id.registerPassword)).getText().toString();
                final String street = ((TextView) findViewById(R.id.registerStreet)).getText().toString();
                final String zipCode = ((TextView) findViewById(R.id.registerZipCode)).getText().toString();
                final String city = ((TextView) findViewById(R.id.registerCity)).getText().toString();
                final String restaurantName = ((TextView) findViewById(R.id.registerRestaurantName)).getText().toString();
                final String ustId = ((TextView) findViewById(R.id.registerUstId)).getText().toString();

                Address address =  new Address(street, zipCode, city, "Germany");
                Restaurant restaurant = new Restaurant(restaurantName, address, ustId);
                User user = new User(email, User.hashPassword(password), firstName, lastName, address, false, restaurant);

                resManager.createRestaurant(restaurant);
                addressManager.createAddress(address);
                userManager.createUser(user);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

}
