package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant_app.activities.DrinkDetailActivity;
import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.models.Drink;

import java.util.List;

/**
 *
 * @author Jonas Mitschke
 * @content adapter for drink data to listview-item
 */
public class DrinkListAdapter extends ArrayAdapter<Drink> {
    public DrinkListAdapter(Context context, List<Drink> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_drinks,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.drink_name);
        TextView priceTextView = convertView.findViewById(R.id.drink_price);
        Button viewDrinkButton = convertView.findViewById(R.id.viewDrinkButton);

        final Drink drink = getItem(position);

        viewDrinkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DrinkDetailActivity.class);
                intent.putExtra("drinkID", drink.getDrinkId());
                v.getContext().startActivity(intent);
            }
        });

        nameTextView.setText(drink.getName());
        String priceText = drink.getPrice() + " €";
        priceTextView.setText(priceText);

        return convertView;
    }
}