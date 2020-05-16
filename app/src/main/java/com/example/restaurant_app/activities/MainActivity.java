package com.example.restaurant_app.activities;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;import com.example.restaurant_app.firestore.FoodFirestoreManager;import com.example.restaurant_app.firestore.RestaurantFirestoreManager;import com.example.restaurant_app.firestore.UserFirestoreManager;import com.example.restaurant_app.models.User;public class MainActivity extends AppCompatActivity {    private FoodFirestoreManager foodFirestoreManager;    private RestaurantFirestoreManager resManager;    private UserFirestoreManager userManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        /* test implementations        foodFirestoreManager = FoodFirestoreManager.newInstance();        resManager = RestaurantFirestoreManager.newInstance();        userManager = UserFirestoreManager.newInstance();        Restaurant res1 = new Restaurant("1","Test Pizzeria 1","1","1234567890");        Food food1 = new Food(res1, "food12345", 3.99, "food 1 description", "https://test.de");        resManager.createRestaurant(res1);        foodFirestoreManager.createFood(food1);        Task<DocumentSnapshot> doc = FirebaseFirestore.getInstance().collection("food").document("4LGJNpl29WCOMR59cYrG").get();        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {            @Override            public void onSuccess(DocumentSnapshot documentSnapshot) {                Food ret_food = documentSnapshot.toObject(Food.class);                System.out.println(ret_food.getName());                System.out.println(ret_food.getRestaurant().getRestaurantName());                System.out.println(ret_food.getRestaurant().getTaxNumber());            }        });        */        final Button button = findViewById(R.id.registerButton);        button.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);                v.getContext().startActivity(intent);            }        });        final Button test_button = findViewById(R.id.testButton);        test_button.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                final String email = ((TextView) findViewById(R.id.testEmailTextbox)).getText().toString();                userManager.getUserByEmail(email, new UserFirestoreManager.GetUserByEmailCallback() {                    @Override                    public void onCallback(User user) {                        ((TextView) findViewById(R.id.returnedEmailText)).setText(user.getEmail());                    }                });            }        });        final Button viewUserButton = findViewById(R.id.viewUserButton);        viewUserButton.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), UserListActivity.class);                v.getContext().startActivity(intent);            }        });    }}