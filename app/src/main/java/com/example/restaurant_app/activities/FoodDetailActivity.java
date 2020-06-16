package com.example.restaurant_app.activities;import android.annotation.SuppressLint;import android.app.Activity;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.database.Cursor;import android.graphics.Bitmap;import android.graphics.BitmapFactory;import android.graphics.drawable.BitmapDrawable;import android.net.Uri;import android.os.Bundle;import android.provider.MediaStore;import android.view.View;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.ImageView;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.CategoryFirestoreManager;import com.example.restaurant_app.firestore.FoodFirestoreManager;import com.example.restaurant_app.firestore.PictureFirestoreManager;import com.example.restaurant_app.models.CategorySpinner;import com.example.restaurant_app.models.Drink;import com.example.restaurant_app.models.Food;import com.example.restaurant_app.models.User;import com.google.gson.Gson;import java.util.ArrayList;/** * * @author Simon Rothmann * @content activity for food-detail */public class FoodDetailActivity extends Activity {    private static final int GALLERY_REQUEST_CODE = 1;    private FoodFirestoreManager foodManager;    private CategoryFirestoreManager categoryManager;    private Spinner spinner;    private PictureFirestoreManager pictureManager;    /**     *     * @param savedInstanceState state, containing the specific foodId     */    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.food_detail);        foodManager = FoodFirestoreManager.newInstance();        categoryManager = CategoryFirestoreManager.newInstance();        pictureManager = PictureFirestoreManager.newInstance();        final String foodID = getIntent().getExtras().getString("foodID");        final Context context = getBaseContext();        final Activity this_activity = this;        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        if (foodID == null) throw new AssertionError();        /**         * checks if you want to update existing food         */        if(!foodID.equals("0")) {            foodManager.getFoodById(foodID, new FoodFirestoreManager.GetFoodByIdCallback() {                @SuppressLint("SetTextI18n")                @Override                public void onCallback(final Food food) {                    ((TextView) findViewById(R.id.foodUpdateName)).setText(food.getName());                    ((TextView) findViewById(R.id.foodUpdatePrice)).setText(Double.toString(food.getPrice()));                    ((TextView) findViewById(R.id.foodUpdateDescription)).setText(food.getDescription());                    pictureManager.downloadImage(food.getPictureUrl(), new PictureFirestoreManager.DownloadCallback() {                        @Override                        public void onCallback(Bitmap picture) {                            ((ImageView) findViewById(R.id.foodPicture)).setImageBitmap(picture);                        }                    });                    categoryManager.getCategoriesByRestaurantIdSpinner(curUser.getRestaurant().getRestaurantId(), new CategoryFirestoreManager.GetCategoryByRestaurantForSpinnerCallback(){                        @Override                        public void onCallback(ArrayList<CategorySpinner> categories) {                            ArrayAdapter<CategorySpinner> adapter = new ArrayAdapter<CategorySpinner>(context, android.R.layout.simple_spinner_dropdown_item, categories);                            spinner = (Spinner) findViewById(R.id.foodUpdateCategory);                            spinner.setAdapter(adapter);                        }                        @Override                        public void onFailureCallback(Exception e) {                            Intent intent = new Intent(this_activity, FoodListActivity.class);                            this_activity.startActivity(intent);                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);                            toast.show();                        }                    });                    final ImageView foodPicture = findViewById(R.id.foodPicture);                    foodPicture.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View view) {                            pickFromGallery();                        }                    });                    final Button saveButton = findViewById(R.id.foodUpdateSaveButton);                    saveButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View v) {                            final String name = ((TextView) findViewById(R.id.foodUpdateName)).getText().toString();                            String price_help = ((TextView) findViewById(R.id.foodUpdatePrice)).getText().toString();                            if (price_help.equals(""))                                price_help = "-1";                            final Double price = Double.parseDouble(price_help);                            final String description = ((TextView) findViewById(R.id.foodUpdateDescription)).getText().toString();                            final Bitmap picture = ((BitmapDrawable)((ImageView) findViewById(R.id.foodPicture)).getDrawable()).getBitmap();                            Boolean success = pictureManager.updateImage(food.getPictureUrl() ,picture);                            if (success){                                Spinner mySpinner = (Spinner) findViewById(R.id.foodUpdateCategory);                                CategorySpinner categorySpinnerItem = (CategorySpinner) mySpinner.getSelectedItem();                                food.setName(name);                                food.setCategoryId(categorySpinnerItem.getId());                                food.setPrice(price);                                food.setDescription(description);                                String checkError = checkError(food);                                if (!checkError.equals("")){                                    Toast toast = Toast.makeText(getApplicationContext(), checkError, Toast.LENGTH_LONG);                                    toast.show();                                }                                else {                                    foodManager.updateFood(food);                                    Intent intent = new Intent(v.getContext(), FoodListActivity.class);                                    v.getContext().startActivity(intent);                                }                            } else {                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_picture_upload), Toast.LENGTH_LONG);                                toast.show();                            }                        }                    });                    final Button deleteButton = findViewById(R.id.foodUpdateDeleteButton);                    deleteButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(final View v) {                            if (curUser.getRestaurant().getRestaurantId().equals(food.getRestaurantId())) {                                AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailActivity.this);                                builder.setMessage(R.string.dialog_delete_food)                                        .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {                                            public void onClick(DialogInterface dialog, int id) {                                                pictureManager.deleteImage(food.getPictureUrl());                                                foodManager.deleteFood(food.getFoodId());                                                Intent intent = new Intent(v.getContext(), FoodListActivity.class);                                                v.getContext().startActivity(intent);                                            }                                        })                                        .setNegativeButton(R.string.button_cancel, null).show();                            } else {                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_permition), Toast.LENGTH_LONG);                                toast.show();                            }                        }                    });                }                @Override                public void onFailureCallback(Exception e) {                    Intent intent = new Intent(this_activity, FoodListActivity.class);                    this_activity.startActivity(intent);                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);                    toast.show();                }            });        }        /**         * else you want to create a new food         */        else {            findViewById(R.id.foodUpdateDeleteButton).setVisibility(View.INVISIBLE);            categoryManager.getCategoriesByRestaurantIdSpinner(curUser.getRestaurant().getRestaurantId(), new CategoryFirestoreManager.GetCategoryByRestaurantForSpinnerCallback(){                @Override                public void onCallback(ArrayList<CategorySpinner> categories) {                    if (categories.size() > 0) {                        ArrayAdapter<CategorySpinner> adapter = new ArrayAdapter<CategorySpinner>(context, android.R.layout.simple_spinner_dropdown_item, categories);                        spinner = (Spinner) findViewById(R.id.foodUpdateCategory);                        spinner.setAdapter(adapter);                    } else {                        Intent intent = new Intent(this_activity, FoodListActivity.class);                        this_activity.startActivity(intent);                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_no_category_available), Toast.LENGTH_LONG);                        toast.show();                    }                }                @Override                public void onFailureCallback(Exception e) {                    Intent intent = new Intent(this_activity, FoodListActivity.class);                    this_activity.startActivity(intent);                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);                    toast.show();                }            });            final ImageView foodPicture = findViewById(R.id.foodPicture);            foodPicture.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View view) {                    pickFromGallery();                }            });            final Button saveButton = findViewById(R.id.foodUpdateSaveButton);            saveButton.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View view) {                    final String name = ((TextView) findViewById(R.id.foodUpdateName)).getText().toString();                    String price_help = ((TextView) findViewById(R.id.foodUpdatePrice)).getText().toString();                    if (price_help.equals(""))                        price_help = "-1";                    final Double price = Double.parseDouble(price_help);                    final String description = ((TextView) findViewById(R.id.foodUpdateDescription)).getText().toString();                    final BitmapDrawable pictureBitmapDrawable = (BitmapDrawable)((ImageView) findViewById(R.id.foodPicture)).getDrawable();                    if (pictureBitmapDrawable == null) {                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_picture_set), Toast.LENGTH_LONG);                        toast.show();                    } else {                        String pictureUrl = pictureManager.uploadImage(pictureBitmapDrawable.getBitmap());                        if (pictureUrl == null) {                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error_picture_upload), Toast.LENGTH_LONG);                            toast.show();                        } else {                            Spinner mySpinner = (Spinner) findViewById(R.id.foodUpdateCategory);                            CategorySpinner categorySpinnerItem = (CategorySpinner) mySpinner.getSelectedItem();                            Food newFood = new Food(curUser.getRestaurant().getRestaurantId(), categorySpinnerItem.getId(), name, price, description, pictureUrl);                            String checkError = checkError(newFood);                            if (!checkError.equals("")){                                Toast toast = Toast.makeText(getApplicationContext(), checkError, Toast.LENGTH_LONG);                                toast.show();                            }                            else {                                foodManager.createFood(newFood);                                Intent intent = new Intent(view.getContext(), FoodListActivity.class);                                view.getContext().startActivity(intent);                            }                        }                    }                }            });        }    }    //picture picker    private void pickFromGallery(){        //Create an Intent with action as ACTION_PICK        Intent intent=new Intent(Intent.ACTION_PICK);        // Sets the type as image/*. This ensures only components of type image are selected        intent.setType("image/*");        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.        String[] mimeTypes = {"image/jpeg", "image/png"};        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);        // Launching the Intent        startActivityForResult(intent, GALLERY_REQUEST_CODE);    }    @Override    public void onActivityResult(int requestCode,int resultCode,Intent data){        // Result code is RESULT_OK only if the user selects an Image        if (resultCode == Activity.RESULT_OK)            switch (requestCode){                case GALLERY_REQUEST_CODE:                    //data.getData returns the content URI for the selected Image                    Uri selectedImageUri = data.getData();                    String[] filePathColumn = { MediaStore.Images.Media.DATA };                    // Get the cursor                    Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);                    // Move to first row                    cursor.moveToFirst();                    //Get the column index of MediaStore.Images.Media.DATA                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);                    //Gets the String value in the column                    String imgDecodableString = cursor.getString(columnIndex);                    cursor.close();                    // Set the Image in ImageView after decoding the String                    ImageView foodPicture = findViewById(R.id.foodPicture);                    foodPicture.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));                    break;            }    }    private String checkError(Food toCheck){        if(toCheck.getName().equals(""))            return getString(R.string.foodName_empty);        if (toCheck.getPrice() == -1)            return getString(R.string.foodPrice_empty);        if(toCheck.getDescription().equals(""))            return getString(R.string.foodDescription_empty);        return "";    }}