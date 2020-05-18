package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.activities.UserDetailActivity;
import com.example.restaurant_app.models.User;

import java.util.List;

/**
 *
 * @author Jonas Mitschke
 * @content adapter for user data to listview-item
 */
public class UserListAdapter extends ArrayAdapter<User> {
    public UserListAdapter(Context context, List<User> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_users,parent,false);
        }

        TextView emailTextView = convertView.findViewById(R.id.user_email);
        TextView nameTextView = convertView.findViewById(R.id.user_full_name);
        Button viewUserButton = convertView.findViewById(R.id.viewUserButton);

        final User user = getItem(position);

        viewUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                intent.putExtra("userEmail", user.getEmail());
                v.getContext().startActivity(intent);
            }
        });

        emailTextView.setText(user.getEmail());
        String nameText = user.getFirstName() + " " + user.getLastName();
        nameTextView.setText(nameText);

        return convertView;
    }
}