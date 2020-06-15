package com.example.restaurant_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.MenuDrinkListAdapter;
import com.example.restaurant_app.adapters.MenuFoodListAdapter;
import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.firestore.DrinkFirestoreManager;
import com.example.restaurant_app.firestore.FoodFirestoreManager;
import com.example.restaurant_app.firestore.TableFirestoreManager;
import com.example.restaurant_app.models.Drink;
import com.example.restaurant_app.models.Food;
import com.example.restaurant_app.models.Table;

import java.util.List;

/**
 *
 * @author  Simon Rothmann
 * @content activity for menu-page
 */
public class MenuActivity extends AppCompatActivity {

    private TableFirestoreManager tableFirestoreManager;
    private FoodFirestoreManager foodFirestoreManager;
    private DrinkFirestoreManager drinkFirestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        final Activity this_activity = this;
        Context context = getBaseContext();
        final String tableID = SharedPreferencesAdapter.getDefaults("currentTable", context);

        tableFirestoreManager = TableFirestoreManager.newInstance();
        foodFirestoreManager = FoodFirestoreManager.newInstance();
        drinkFirestoreManager = DrinkFirestoreManager.newInstance();

        tableFirestoreManager.getTableById(tableID, new TableFirestoreManager.GetTableByIdCallback() {
            @Override
            public void onCallback(Table table) {
                final Button foodButton = findViewById(R.id.menuFoodButton);
                foodButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foodFirestoreManager.getFoodsByRestaurantId(table.getRestaurantId(), new FoodFirestoreManager.GetFoodsByRestaurantCallback() {
                            @Override
                            public void onCallback(List<Food> foods) {
                                if (foods.size() > 0) {
                                    ListView menuListView = findViewById(R.id.menuListView);
                                    MenuFoodListAdapter foodAdapter = new MenuFoodListAdapter(MenuActivity.this, foods);
                                    menuListView.setAdapter(foodAdapter);
                                }
                            }

                            @Override
                            public void onFailureCallback(Exception e) {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                    }
                });

                final Button drinkButton = findViewById(R.id.menuDrinkButton);
                drinkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drinkFirestoreManager.getDrinksByRestaurantId(table.getRestaurantId(), new DrinkFirestoreManager.GetDrinksByRestaurantCallback() {
                            @Override
                            public void onCallback(List<Drink> drinks) {
                                if (drinks.size() > 0) {
                                    ListView menuListView = findViewById(R.id.menuListView);
                                    MenuDrinkListAdapter drinkAdapter = new MenuDrinkListAdapter(MenuActivity.this, drinks);
                                    menuListView.setAdapter(drinkAdapter);
                                }
                            }

                            @Override
                            public void onFailureCallback(Exception e) {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailureCallback(Exception e) {
                Intent intent = new Intent(this_activity, MainActivity.class);
                this_activity.startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
