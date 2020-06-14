package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.restaurant_app.activities.DrinkDetailActivity;
import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.firestore.PictureFirestoreManager;
import com.example.restaurant_app.models.Drink;

import java.util.List;

public class MenuDrinkListAdapter extends ArrayAdapter<Drink> {

    private PictureFirestoreManager pictureFirestoreManager;
    public MenuDrinkListAdapter(Context context, List<Drink> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_menu ,parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.itemMenuPicture);
        TextView nameTextView = convertView.findViewById(R.id.itemMenuName);
        TextView priceTextView = convertView.findViewById(R.id.itemMenuPrice);

        final Drink drink = getItem(position);

        ConstraintLayout itemMenuGroupLayout = convertView.findViewById(R.id.itemMenuGroup);

        itemMenuGroupLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DrinkDetailActivity.class);
                intent.putExtra("drinkID", drink.getDrinkId());
                v.getContext().startActivity(intent);
            }
        });

        pictureFirestoreManager = PictureFirestoreManager.newInstance();
        pictureFirestoreManager.downloadImage(drink.getPictureUrl(), new PictureFirestoreManager.DownloadCallback() {
            @Override
            public void onCallback(Bitmap picture) {
                imageView.setImageBitmap(picture);
            }
        });
        nameTextView.setText(drink.getName());
        String priceText = drink.getPrice() + " €";
        priceTextView.setText(priceText);

        return convertView;
    }
}
