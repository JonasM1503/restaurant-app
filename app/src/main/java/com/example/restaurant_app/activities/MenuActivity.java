package com.example.restaurant_app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.FoodListAdapter;
import com.example.restaurant_app.adapters.MenuDrinkListAdapter;
import com.example.restaurant_app.adapters.MenuFoodListAdapter;
import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.firestore.DrinkFirestoreManager;
import com.example.restaurant_app.firestore.FoodFirestoreManager;
import com.example.restaurant_app.firestore.TableFirestoreManager;
import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Drink;
import com.example.restaurant_app.models.Food;
import com.example.restaurant_app.models.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Simon Rothmann
 * @content activity for menu-page
 */
public class MenuActivity extends AppCompatActivity {
    private TableFirestoreManager tableFirestoreManager;
    private static Button foodButton;
    private static Button drinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        Context context = getBaseContext();
        final String tableID = SharedPreferencesAdapter.getDefaults("currentTable", context);
        tableFirestoreManager = TableFirestoreManager.newInstance();
        final Table[] table1 = new Table[1];
        tableFirestoreManager.getTableById(tableID, new TableFirestoreManager.GetTableByIdCallback() {
            @Override
            public void onCallback(Table table) {
                table1[0] = table;
            }

            @Override
            public void onFailureCallback(Exception e) {

            }
        });

        foodButton = findViewById(R.id.menuFoodButton);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection(CollectionNames.foodCollection)
                        .whereEqualTo("restaurantId", table1[0].getRestaurantId()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<Food> return_foods = new ArrayList<>();
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot document : task.getResult()) {
                                        Food food = document.toObject(Food.class);
                                        return_foods.add(food);
                                    }
                                    ListView menuListView = findViewById(R.id.menuListView);
                                    MenuFoodListAdapter foodAdapter = new MenuFoodListAdapter(MenuActivity.this, return_foods);
                                    menuListView.setAdapter(foodAdapter);
                                }
                            }
                        });
            }
        });

        drinkButton = findViewById(R.id.menuDrinkButton);
        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection(CollectionNames.drinkCollection)
                        .whereEqualTo("restaurantId", table1[0].getRestaurantId()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<Drink> return_drinks = new ArrayList<>();
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot document : task.getResult()) {
                                        Drink drink = document.toObject(Drink.class);
                                        return_drinks.add(drink);
                                    }
                                    ListView menuListView = findViewById(R.id.menuListView);
                                    MenuDrinkListAdapter drinkAdapter = new MenuDrinkListAdapter(MenuActivity.this, return_drinks);
                                    menuListView.setAdapter(drinkAdapter);
                                }
                            }
                        });
            }
        });

    }
}
