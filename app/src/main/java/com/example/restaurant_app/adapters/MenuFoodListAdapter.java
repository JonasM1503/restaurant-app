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

import com.example.restaurant_app.activities.MenuDetailActivity;
import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.firestore.PictureFirestoreManager;
import com.example.restaurant_app.models.Food;

import java.util.List;

/**
 *
 * @author  Simon Rothmann
 * @content adapter for MenuList
 */
public class MenuFoodListAdapter extends ArrayAdapter<Food>{
    private PictureFirestoreManager pictureFirestoreManager;
    public MenuFoodListAdapter(Context context, List<Food> object){
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

        ConstraintLayout itemMenuGroupLayout = convertView.findViewById(R.id.itemMenuGroup);

        final Food food = getItem(position);

        itemMenuGroupLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuDetailActivity.class);
                intent.putExtra("foodID", food.getFoodId());
                v.getContext().startActivity(intent);
            }
        });

        pictureFirestoreManager = PictureFirestoreManager.newInstance();
        pictureFirestoreManager.downloadImage(food.getPictureUrl(), new PictureFirestoreManager.DownloadCallback() {
            @Override
            public void onCallback(Bitmap picture) {
                imageView.setImageBitmap(picture);
            }
        });
        nameTextView.setText(food.getName());
        String priceText = food.getPrice() + " €";
        priceTextView.setText(priceText);

        return convertView;
    }
}
