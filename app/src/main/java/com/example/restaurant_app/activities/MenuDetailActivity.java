package com.example.restaurant_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.firestore.DrinkFirestoreManager;
import com.example.restaurant_app.firestore.FoodFirestoreManager;
import com.example.restaurant_app.firestore.PictureFirestoreManager;
import com.example.restaurant_app.models.Drink;
import com.example.restaurant_app.models.Food;
import com.example.restaurant_app.models.OrderPos;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Jonas Mitschke
 * @content activity for menu-item
 */
public class MenuDetailActivity extends AppCompatActivity {

    private FoodFirestoreManager foodFirestoreManager;
    private DrinkFirestoreManager drinkFirestoreManager;
    private PictureFirestoreManager pictureManager;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail);

        Context context = getBaseContext();
        final Activity this_activity = this;
        final String foodID = getIntent().getExtras().getString("foodID");
        final String drinkID = getIntent().getExtras().getString("drinkID");

        foodFirestoreManager = FoodFirestoreManager.newInstance();
        drinkFirestoreManager = DrinkFirestoreManager.newInstance();
        pictureManager = PictureFirestoreManager.newInstance();

        if (foodID != null) {
            foodFirestoreManager.getFoodById(foodID, new FoodFirestoreManager.GetFoodByIdCallback() {
                @Override
                public void onCallback(Food food) {
                    ((TextView) findViewById(R.id.menuItemNameText)).setText(food.getName());
                    ((TextView) findViewById(R.id.menuItemPriceText)).setText(Double.toString(food.getPrice()));
                    ((TextView) findViewById(R.id.menuItemDescriptionText)).setText(food.getDescription());
                    ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));

                    pictureManager.downloadImage(food.getPictureUrl(), new PictureFirestoreManager.DownloadCallback() {
                        @Override
                        public void onCallback(Bitmap picture) {
                            ((ImageView) findViewById(R.id.menuItemPicture)).setImageBitmap(picture);
                        }
                    });

                    final Button decreaseQuantityButton = findViewById(R.id.menuItemMinusButton);
                    decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (quantity > 1) {
                                quantity--;
                                ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_min_amount_reached), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });

                    final Button increaseQuantityButton = findViewById(R.id.menuItemPlusButton);
                    increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (quantity < 15) {
                                quantity++;
                                ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_max_amount_reached), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });

                    final Button addToBasketButton = findViewById(R.id.menuItemAddButton);
                    addToBasketButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String wish = ((TextView) findViewById(R.id.menuItemWishText)).getText().toString();
                            final int quantity = Integer.parseInt(((TextView) findViewById(R.id.menuItemQuantityText)).getText().toString());

                            // get orderPos-list from SharedPreferences
                            Gson gson = new Gson();
                            List<String> orderPosStringList = SharedPreferencesAdapter.getListDefaults("orderPosList", context);
                            List<OrderPos> orderPosList = new ArrayList<>();
                            for (int i=0; i<orderPosStringList.size(); i++) {
                                orderPosList.add(gson.fromJson(orderPosStringList.get(i), OrderPos.class));
                            }

                            // add to SharedPreferences
                            OrderPos orderPos = new OrderPos(food, wish, quantity);
                            orderPosList.add(orderPos);
                            List<String> orderPosStringListNew = new ArrayList<>();
                            for (int i=0; i<orderPosList.size(); i++) {
                                orderPosStringListNew.add(gson.toJson(orderPosList.get(i)));
                            }
                            SharedPreferencesAdapter.setListDefaults("orderPosList", orderPosStringListNew, context);

                            Intent intent = new Intent(this_activity, MenuActivity.class);
                            this_activity.startActivity(intent);
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_item_added), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }

                @Override
                public void onFailureCallback(Exception e) {
                    e.printStackTrace();
                }
            });
        } else if (drinkID != null) {
            drinkFirestoreManager.getDrinkById(drinkID, new DrinkFirestoreManager.GetDrinkByIdCallback() {
                @Override
                public void onCallback(Drink drink) {
                    ((TextView) findViewById(R.id.menuItemNameText)).setText(drink.getName());
                    ((TextView) findViewById(R.id.menuItemPriceText)).setText(Double.toString(drink.getPrice()));
                    ((TextView) findViewById(R.id.menuItemDescriptionText)).setText(drink.getDescription());
                    ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));

                    pictureManager.downloadImage(drink.getPictureUrl(), new PictureFirestoreManager.DownloadCallback() {
                        @Override
                        public void onCallback(Bitmap picture) {
                            ((ImageView) findViewById(R.id.menuItemPicture)).setImageBitmap(picture);
                        }
                    });

                    final Button decreaseQuantityButton = findViewById(R.id.menuItemMinusButton);
                    decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (quantity > 1) {
                                quantity--;
                                ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_min_amount_reached), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });

                    final Button increaseQuantityButton = findViewById(R.id.menuItemPlusButton);
                    increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (quantity < 15) {
                                quantity++;
                                ((TextView) findViewById(R.id.menuItemQuantityText)).setText(Integer.toString(quantity));
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_max_amount_reached), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });

                    final Button addToBasketButton = findViewById(R.id.menuItemAddButton);
                    addToBasketButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String wish = ((TextView) findViewById(R.id.menuItemWishText)).getText().toString();
                            final int quantity = Integer.parseInt(((TextView) findViewById(R.id.menuItemQuantityText)).getText().toString());

                            // get orderPos-list from SharedPreferences
                            Gson gson = new Gson();
                            List<String> orderPosStringList = SharedPreferencesAdapter.getListDefaults("orderPosList", context);
                            List<OrderPos> orderPosList = new ArrayList<>();
                            for (int i=0; i<orderPosStringList.size(); i++) {
                                orderPosList.add(gson.fromJson(orderPosStringList.get(i), OrderPos.class));
                            }

                            // add to SharedPreferences
                            OrderPos orderPos = new OrderPos(drink, wish, quantity);
                            orderPosList.add(orderPos);
                            List<String> orderPosStringListNew = new ArrayList<>();
                            for (int i=0; i<orderPosList.size(); i++) {
                                orderPosStringListNew.add(gson.toJson(orderPosList.get(i)));
                            }
                            SharedPreferencesAdapter.setListDefaults("orderPosList", orderPosStringListNew, context);

                            Intent intent = new Intent(this_activity, MenuActivity.class);
                            this_activity.startActivity(intent);
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_item_added), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }

                @Override
                public void onFailureCallback(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            Intent intent = new Intent(this_activity, MenuActivity.class);
            this_activity.startActivity(intent);
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
