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

        /**
         * switch to user management overview
         */
        final Button viewUsersButton = findViewById(R.id.viewUserButton);
        if (curUser.checkWhetherAdmin()){
            ((Button) findViewById(R.id.viewUserButton)).setText(getResources().getString(R.string.view_users_button));
        } else {
            ((Button) findViewById(R.id.viewUserButton)).setText(getResources().getString(R.string.view_user_profile_button));
        }
        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (curUser.checkWhetherAdmin()){
                    Intent intent = new Intent(v.getContext(), UserListActivity.class);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                    intent.putExtra("userEmail", curUser.getEmail());
                    v.getContext().startActivity(intent);
                }
            }
        });

        /**
         * switch to change password view
         */
        final Button updatePWButton = findViewById(R.id.updatePWButton);
        updatePWButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PasswordChangeActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /**
         * switch to categories overview
         */
        final Button viewCategoriesButton = findViewById(R.id.viewCategoryButton);
        viewCategoriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryListActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /**
         * switch to drinks overview
         */
        final Button viewDrinksButton = findViewById(R.id.viewDrinkButton);
        viewDrinksButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DrinkListActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /**
         * switch to food overview
         */
        final Button viewFoodsButton = findViewById(R.id.viewFoodButton);
        viewFoodsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FoodListActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /**
         * switch to tables overview
         */
        final Button viewTablesButton = findViewById(R.id.viewTableButton);
        viewTablesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TableListActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        /**
         * logout of app
         */
        final Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getBaseContext();
                SharedPreferencesAdapter.setDefaults("currentUser", null, context);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
