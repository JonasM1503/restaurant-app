package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.firestore.DrinkFirestoreManager;
import com.example.restaurant_app.firestore.FoodFirestoreManager;
import com.example.restaurant_app.models.Drink;
import com.example.restaurant_app.models.Food;
import com.example.restaurant_app.models.OrderPos;

import java.util.List;
/**
 *
 * @author Simon Rothmann
 * @content adapter for shoppingCart-List
 */
public class ShopingCartAdapter extends ArrayAdapter<OrderPos> {


    public ShopingCartAdapter(Context context, List<OrderPos> object) {
        super(context, 0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_shoppingcart, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.shopingItemName);
        TextView priceTextView = convertView.findViewById(R.id.shopingItemPrice);
        TextView quantityTextview = convertView.findViewById(R.id.shopingCartQuantity);
        ImageButton deleteItemButton = convertView.findViewById(R.id.deleteItemButton);

        final OrderPos orderPos = getItem(position);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (orderPos.getDrinkId() != null) {
            DrinkFirestoreManager drinkFirestoreManager = DrinkFirestoreManager.newInstance();
            drinkFirestoreManager.getDrinkById(orderPos.getDrinkId(), new DrinkFirestoreManager.GetDrinkByIdCallback() {
                @Override
                public void onCallback(Drink drink) {
                    nameTextView.setText(drink.getName());
                    priceTextView.setText(drink.getPrice().toString() + " €");
                    quantityTextview.setText(Integer.toString(orderPos.getQuantity()));
                }

                @Override
                public void onFailureCallback(Exception e) {

                }
            });
        } else if (orderPos.getFoodId() != null) {
            FoodFirestoreManager foodFirestoreManager = FoodFirestoreManager.newInstance();
            foodFirestoreManager.getFoodById(orderPos.getFoodId(), new FoodFirestoreManager.GetFoodByIdCallback() {
                @Override
                public void onCallback(Food food) {
                    nameTextView.setText(food.getName());
                    priceTextView.setText(food.getPrice().toString() + " €");
                    quantityTextview.setText(Integer.toString(orderPos.getQuantity()));
                }

                @Override
                public void onFailureCallback(Exception e) {

                }
            });
        }
        return convertView;
    }
}
