package com.example.restaurant_app.activities;import android.annotation.SuppressLint;import android.app.Activity;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.TextView;import android.widget.Toast;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.DrinkFirestoreManager;import com.example.restaurant_app.models.Drink;import com.example.restaurant_app.models.User;import com.google.gson.Gson;/** * * @author Jonas Mitschke * @content activity for drink-detail */public class DrinkDetailActivity extends Activity {    private DrinkFirestoreManager drinkManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.drink_detail);        drinkManager = DrinkFirestoreManager.newInstance();        final String drinkID = getIntent().getExtras().getString("drinkID");        Context context = getBaseContext();        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        drinkManager.getDrinkById(drinkID, new DrinkFirestoreManager.GetDrinkByIdCallback() {            @SuppressLint("SetTextI18n")            @Override            public void onCallback(final Drink drink) {                ((TextView) findViewById(R.id.drinkUpdateName)).setText(drink.getName());                ((TextView) findViewById(R.id.drinkUpdatePrice)).setText(Double.toString(drink.getPrice()));                ((TextView) findViewById(R.id.drinkUpdateDescription)).setText(drink.getDescription());                ((TextView) findViewById(R.id.drinkUpdatePicture)).setText(drink.getPictureUrl());                final Button saveButton = findViewById(R.id.drinkUpdateSaveButton);                saveButton.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        final String name = ((TextView) findViewById(R.id.drinkUpdateName)).getText().toString();                        String price_help = ((TextView) findViewById(R.id.drinkUpdatePrice)).getText().toString();                        final Double price = Double.parseDouble(price_help);                        final String description = ((TextView) findViewById(R.id.drinkUpdateDescription)).getText().toString();                        final String pictureURL = ((TextView) findViewById(R.id.drinkUpdatePicture)).getText().toString();                        drink.setName(name);                        drink.setPrice(price);                        drink.setDescription(description);                        drink.setPictureUrl(pictureURL);                        drinkManager.updateDrink(drink);                        Intent intent = new Intent(v.getContext(), DrinkListActivity.class);                        v.getContext().startActivity(intent);                    }                });                final Button deleteButton = findViewById(R.id.drinkUpdateDeleteButton);                deleteButton.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(final View v) {                        if (curUser.getRestaurant() == drink.getRestaurant()){                            AlertDialog.Builder builder = new AlertDialog.Builder(DrinkDetailActivity.this);                            builder.setMessage(R.string.dialog_delete_drink)                                    .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {                                        public void onClick(DialogInterface dialog, int id) {                                            drinkManager.deleteDrink(drink.getDrinkId());                                            Intent intent = new Intent(v.getContext(), DrinkListActivity.class);                                            v.getContext().startActivity(intent);                                        }                                    })                                    .setNegativeButton(R.string.button_cancel, null).show();                        } else {                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_permition), Toast.LENGTH_LONG);                            toast.show();                        }                    }                });            }        });    }}