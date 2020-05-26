package com.example.restaurant_app.activities;import android.annotation.SuppressLint;import android.app.Activity;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.CategoryFirestoreManager;import com.example.restaurant_app.firestore.FoodFirestoreManager;import com.example.restaurant_app.models.CategorySpinner;import com.example.restaurant_app.models.Food;import com.example.restaurant_app.models.User;import com.google.gson.Gson;import java.util.ArrayList;/** * * @author Simon Rothmann * @content activity for food-detail */public class FoodDetailActivity extends Activity {    private FoodFirestoreManager foodManager;    private CategoryFirestoreManager categoryManager;    private Spinner spinner;    private String categoryId;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.food_detail);        foodManager = FoodFirestoreManager.newInstance();        categoryManager = CategoryFirestoreManager.newInstance();        final String foodID = getIntent().getExtras().getString("foodID");        final Context context = getBaseContext();        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        if (foodID == null) throw new AssertionError();        if(!foodID.equals("0")) {            foodManager.getFoodById(foodID, new FoodFirestoreManager.GetFoodByIdCallback() {                @SuppressLint("SetTextI18n")                @Override                public void onCallback(final Food food) {                    ((TextView) findViewById(R.id.foodUpdateName)).setText(food.getName());                    ((TextView) findViewById(R.id.foodUpdatePrice)).setText(Double.toString(food.getPrice()));                    ((TextView) findViewById(R.id.foodUpdateDescription)).setText(food.getDescription());                    ((TextView) findViewById(R.id.foodUpdatePicture)).setText(food.getPictureURL());                    categoryManager.getCategoriesByRestaurantId(curUser.getRestaurant().getRestaurantId(), new CategoryFirestoreManager.GetCategoryByRestaurantCallback(){                        @Override                        public void onCallback(ArrayList<CategorySpinner> categories) {                            ArrayAdapter<CategorySpinner> adapter = new ArrayAdapter<CategorySpinner>(context, android.R.layout.simple_spinner_dropdown_item, categories);                            spinner = (Spinner) findViewById(R.id.foodUpdateCategory);                            spinner.setAdapter(adapter);                        }                    });                    final Button saveButton = findViewById(R.id.foodUpdateSaveButton);                    saveButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View v) {                            final String name = ((TextView) findViewById(R.id.foodUpdateName)).getText().toString();                            String price_help = ((TextView) findViewById(R.id.foodUpdatePrice)).getText().toString();                            final Double price = Double.parseDouble(price_help);                            final String description = ((TextView) findViewById(R.id.foodUpdateDescription)).getText().toString();                            final String pictureURL = ((TextView) findViewById(R.id.foodUpdatePicture)).getText().toString();                            Spinner mySpinner = (Spinner) findViewById(R.id.foodUpdateCategory);                            CategorySpinner categorySpinnerItem = (CategorySpinner) mySpinner.getSelectedItem();                            food.setName(name);                            food.setCategoryId(categorySpinnerItem.getId());                            food.setPrice(price);                            food.setDescription(description);                            food.setPictureURL(pictureURL);                            foodManager.updateFood(food);                            Intent intent = new Intent(v.getContext(), FoodListActivity.class);                            v.getContext().startActivity(intent);                        }                    });                    final Button deleteButton = findViewById(R.id.foodUpdateDeleteButton);                    deleteButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(final View v) {                            if (curUser.getRestaurant().getRestaurantId().equals(food.getRestaurantId())) {                                AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailActivity.this);                                builder.setMessage(R.string.dialog_delete_food)                                        .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {                                            public void onClick(DialogInterface dialog, int id) {                                                foodManager.deleteFood(food.getFoodId());                                                Intent intent = new Intent(v.getContext(), FoodListActivity.class);                                                v.getContext().startActivity(intent);                                            }                                        })                                        .setNegativeButton(R.string.button_cancel, null).show();                            } else {                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_permition), Toast.LENGTH_LONG);                                toast.show();                            }                        }                    });                }            });        }        else {            findViewById(R.id.foodUpdateDeleteButton).setVisibility(View.INVISIBLE);            categoryManager.getCategoriesByRestaurantId(curUser.getRestaurant().getRestaurantId(), new CategoryFirestoreManager.GetCategoryByRestaurantCallback(){                @Override                public void onCallback(ArrayList<CategorySpinner> categories) {                    ArrayAdapter<CategorySpinner> adapter = new ArrayAdapter<CategorySpinner>(context, android.R.layout.simple_spinner_dropdown_item, categories);                    spinner = (Spinner) findViewById(R.id.foodUpdateCategory);                    spinner.setAdapter(adapter);                }            });            final Button saveButton = findViewById(R.id.foodUpdateSaveButton);            saveButton.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View view) {                    final String name = ((TextView) findViewById(R.id.foodUpdateName)).getText().toString();                    String price_help = ((TextView) findViewById(R.id.foodUpdatePrice)).getText().toString();                    final Double price = Double.parseDouble(price_help);                    final String description = ((TextView) findViewById(R.id.foodUpdateDescription)).getText().toString();                    final String pictureURL = ((TextView) findViewById(R.id.foodUpdatePicture)).getText().toString();                    Spinner mySpinner = (Spinner) findViewById(R.id.foodUpdateCategory);                    CategorySpinner categorySpinnerItem = (CategorySpinner) mySpinner.getSelectedItem();                    Food newFood = new Food(curUser.getRestaurant().getRestaurantId(), categorySpinnerItem.getId(), name, price, description, pictureURL);                    foodManager.createFood(newFood);                    Intent intent = new Intent(view.getContext(), FoodListActivity.class);                    view.getContext().startActivity(intent);                }            });        }    }}