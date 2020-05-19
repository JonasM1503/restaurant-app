package com.example.restaurant_app.activities;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import androidx.appcompat.app.AppCompatActivity;import com.example.restaurant_app.firestore.FoodFirestoreManager;import com.example.restaurant_app.firestore.RestaurantFirestoreManager;import com.example.restaurant_app.firestore.UserFirestoreManager;/** * * @author Simon Rothmann, Jonas Mitschke * @content activity for start */public class MainActivity extends AppCompatActivity {    private FoodFirestoreManager foodFirestoreManager;    private RestaurantFirestoreManager resManager;    private UserFirestoreManager userManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        final Button button = findViewById(R.id.registerButton);        button.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);                v.getContext().startActivity(intent);            }        });        final Button loginButton = findViewById(R.id.loginButton);        loginButton.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), LoginActivity.class);                v.getContext().startActivity(intent);            }        });    }}