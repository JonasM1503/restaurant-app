package com.example.restaurant_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    public static String testID;

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

                String error = checkError(firstName, lastName, email, password, street, zipCode,
                        city, restaurantName, ustId);
                if (!error.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), error , Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Address address =  new Address(street, zipCode, city, "Germany");
                    addressManager.createAddress(address);

                    Restaurant restaurant = new Restaurant(restaurantName, address, ustId);
                    resManager.createRestaurant(restaurant);

                    User user = new User(email, User.hashPassword(password), firstName, lastName, address, false, restaurant);
                    userManager.createUser(user);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    private static String checkError(String firstName, String lastName, String email,
                                     String password, String street, String zipCode,
                                     String city, String restaurantName, String ustId){
        if(email.equals(""))
            return "Email darf nicht leer sein";
        if (password.equals(""))
            return "Passwort darf nicht leer sein";
        if (firstName.equals(""))
            return "Vorname darf nicht leer sein";
        if (lastName.equals(""))
            return "Nachname darf nicht leer sein";
        if (street.equals(""))
            return "Straße darf nicht leer sein";
        if (zipCode.equals(""))
            return "PLZ darf nicht leer sein";
        if (city.equals(""))
            return "Stadt darf nicht leer sein";
        if (restaurantName.equals(""))
            return "Restaurantname darf nicht leer sein";
        if (ustId.equals(""))
            return "UstId darf nicht leer sein";
        return "";
    }
}
