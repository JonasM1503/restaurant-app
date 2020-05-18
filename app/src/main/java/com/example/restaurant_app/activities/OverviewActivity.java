package com.example.restaurant_app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.models.User;
import com.google.gson.Gson;

public class OverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        TextView sptest = ((TextView) findViewById(R.id.spTest));
        //SharedPreferences test
        Gson gson = new Gson();
        SharedPreferencesAdapter spAdapter = new SharedPreferencesAdapter();
        Context context = getBaseContext();
        User user = gson.fromJson(spAdapter.getDefaults("currentUser",context), User.class);

        if (user != null) {
            sptest.setText(user.getEmail());
        }
    }
}
