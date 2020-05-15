package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.models.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, List<User> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_users,parent,false);
        }

        TextView emailTextView = (TextView) convertView.findViewById(R.id.user_email);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.user_full_name);

        User user = getItem(position);

        emailTextView.setText(user.getEmail());
        String nameText = user.getFirstName() + " " + user.getLastName();
        nameTextView.setText(nameText);

        return convertView;
    }

}