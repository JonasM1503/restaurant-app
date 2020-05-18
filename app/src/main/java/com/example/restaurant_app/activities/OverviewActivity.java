package com.example.restaurant_app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.models.User;
import com.google.gson.Gson;

/**
 *
 * @author Simon Rothmann, Jonas Mitschke
 * @content activity for overview
 */
public class OverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);

        Gson gson = new Gson();
        Context context = getBaseContext();
        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser",context), User.class);

        final Button viewUserButton = findViewById(R.id.viewUserButton);
        if (curUser.isAdmin()){
            ((Button) findViewById(R.id.viewUserButton)).setText(getResources().getString(R.string.view_users_button));
        } else {
            ((Button) findViewById(R.id.viewUserButton)).setText(getResources().getString(R.string.view_user_profile_button));
        }
        viewUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (curUser.isAdmin()){
                    Intent intent = new Intent(v.getContext(), UserListActivity.class);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                    intent.putExtra("userEmail", curUser.getEmail());
                    v.getContext().startActivity(intent);
                }
            }
        });

        final Button updatePWButton = findViewById(R.id.updatePWButton);
        updatePWButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PasswordChangeActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}
