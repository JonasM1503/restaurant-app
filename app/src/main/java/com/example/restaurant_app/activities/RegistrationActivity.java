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
            public void onClick(final View v) {
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

                    Restaurant restaurant = new Restaurant(restaurantName, address, ustId);

                    final User newUser = new User(email, User.hashPassword(password), firstName, lastName, false, restaurant);
                        userManager.getUserByEmail(email, new UserFirestoreManager.GetUserByEmailCallback() {
                            @Override
                            public void onCallback(User user) {
                                if(user == null){
                                    userManager.createUser(newUser);
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    v.getContext().startActivity(intent);
                                }
                                else {
                                    Toast toast = Toast.makeText(
                                            getApplicationContext(),
                                            "Benutzer bereits vorhanden, bitte mit neuer Email Probieren",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        });
                }
            }
        });
    }

    /**
     * Method checking for errors in user input while registration.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param street
     * @param zipCode
     * @param city
     * @param restaurantName
     * @param ustId
     * @return errormessage
     */
    private String checkError(String firstName, String lastName, String email,
                              String password, String street, String zipCode,
                              String city, String restaurantName, String ustId){
        if(email.equals(""))
            return getString(R.string.email_empty);
        if (password.equals(""))
            return getString(R.string.password_empty);
        if (firstName.equals(""))
            return getString(R.string.firstName_empty);
        if (lastName.equals(""))
            return getString(R.string.lastName_empty);
        if (street.equals(""))
            return getString(R.string.street_empty);
        if (zipCode.equals(""))
            return getString(R.string.zipCode_empty);
        if (city.equals(""))
            return getString(R.string.city_empty);
        if (restaurantName.equals(""))
            return getString(R.string.restaurantName_empty);
        if (ustId.equals(""))
            return getString(R.string.ustId_empty);
        return "";
    }
}
