package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant_app.activities.CategoryDetailActivity;
import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.models.Category;

import java.util.List;

/**
 *
 * @author Jonas Mitschke
 * @content adapter for category data to listview-item
 */
public class CategoryListAdapter extends ArrayAdapter<Category> {
    public CategoryListAdapter(Context context, List<Category> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_categories,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.category_name);
        Button viewCategoryButton = convertView.findViewById(R.id.viewCategoryButton);

        final Category category = getItem(position);

        viewCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryDetailActivity.class);
                intent.putExtra("categoryID", category.getCategoryId());
                v.getContext().startActivity(intent);
            }
        });

        nameTextView.setText(category.getName());

        return convertView;
    }
}