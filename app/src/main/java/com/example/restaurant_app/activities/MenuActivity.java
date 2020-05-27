package com.example.restaurant_app.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;

/**
 *
 * @author Jonas Mitschke
 * @content activity for menu-page
 */
public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        Context context = getBaseContext();
        final String tableID = SharedPreferencesAdapter.getDefaults("currentTable", context);

        ((TextView) findViewById(R.id.testTableIDText)).setText(tableID);

    }
}
