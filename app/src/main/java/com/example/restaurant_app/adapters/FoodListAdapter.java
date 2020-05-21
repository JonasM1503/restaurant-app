package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant_app.activities.FoodDetailActivity;
import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.models.Food;

import java.util.List;

/**
 *
 * @author Simon Rothmann
 * @content adapter for food data to listview-item
 */
public class FoodListAdapter extends ArrayAdapter<Food> {
    public FoodListAdapter(Context context, List<Food> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_foods,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.food_name);
        TextView priceTextView = convertView.findViewById(R.id.food_price);
        Button viewFoodButton = convertView.findViewById(R.id.viewFoodButton);

        final Food food = getItem(position);

        viewFoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
                intent.putExtra("foodID", food.getFoodId());
                v.getContext().startActivity(intent);
            }
        });

        nameTextView.setText(food.getName());
        String priceText = food.getPrice() + " €";
        priceTextView.setText(priceText);

        return convertView;
    }
}