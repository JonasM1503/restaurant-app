package com.example.restaurant_app.activities;import android.app.Activity;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.Switch;import android.widget.TextView;import android.widget.Toast;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.AddressFirestoreManager;import com.example.restaurant_app.firestore.RestaurantFirestoreManager;import com.example.restaurant_app.firestore.UserFirestoreManager;import com.example.restaurant_app.models.Address;import com.example.restaurant_app.models.Restaurant;import com.example.restaurant_app.models.User;import com.google.gson.Gson;/** * * @author Jonas Mitschke * @content activity for user-detail */public class UserDetailActivity extends Activity {    private RestaurantFirestoreManager resManager;    private UserFirestoreManager userManager;    private AddressFirestoreManager addressManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.user_detail);        resManager = RestaurantFirestoreManager.newInstance();        userManager = UserFirestoreManager.newInstance();        addressManager = AddressFirestoreManager.newInstance();        final String email = getIntent().getExtras().getString("userEmail");        Context context = getBaseContext();        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        userManager.getUserByEmail(email, new UserFirestoreManager.GetUserByEmailCallback() {            @Override            public void onCallback(final User user) {                ((TextView) findViewById(R.id.userUpdateFirstname)).setText(user.getFirstName());                ((TextView) findViewById(R.id.userUpdateLastname)).setText(user.getLastName());                ((TextView) findViewById(R.id.userUpdateEmail)).setText(user.getEmail());                if (user.getRestaurant().getAddress() != null) {                    ((TextView) findViewById(R.id.userUpdateStreet)).setText(user.getRestaurant().getAddress().getAddressStreet());                    ((TextView) findViewById(R.id.userUpdateZipCode)).setText(user.getRestaurant().getAddress().getAddressZipCode());                    ((TextView) findViewById(R.id.userUpdateCity)).setText(user.getRestaurant().getAddress().getAddressCity());                } else {                    findViewById(R.id.userUpdateStreet).setEnabled(false);                    findViewById(R.id.userUpdateZipCode).setEnabled(false);                    findViewById(R.id.userUpdateCity).setEnabled(false);                }                if (user.getRestaurant() != null) {                    ((TextView) findViewById(R.id.userUpdateRestaurantname)).setText(user.getRestaurant().getRestaurantName());                    ((TextView) findViewById(R.id.userUpdateUstId)).setText(user.getRestaurant().getTaxNumber());                } else {                    findViewById(R.id.userUpdateRestaurantname).setEnabled(false);                    findViewById(R.id.userUpdateUstId).setEnabled(false);                }                if (curUser.checkWhetherAdmin() && !user.checkWhetherAdmin()) {                    findViewById(R.id.isActiveLabel).setVisibility(View.VISIBLE);                    findViewById(R.id.isActiveSwitch).setVisibility(View.VISIBLE);                    ((Switch) findViewById(R.id.isActiveSwitch)).setChecked(user.getIsActive());                }                final Button saveButton = findViewById(R.id.userUpdateSaveButton);                saveButton.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        final String firstName = ((TextView) findViewById(R.id.userUpdateFirstname)).getText().toString();                        final String lastName = ((TextView) findViewById(R.id.userUpdateLastname)).getText().toString();                        final String email = ((TextView) findViewById(R.id.userUpdateEmail)).getText().toString();                        final String street = ((TextView) findViewById(R.id.userUpdateStreet)).getText().toString();                        final String zipCode = ((TextView) findViewById(R.id.userUpdateZipCode)).getText().toString();                        final String city = ((TextView) findViewById(R.id.userUpdateCity)).getText().toString();                        final String restaurantName = ((TextView) findViewById(R.id.userUpdateRestaurantname)).getText().toString();                        final String ustId = ((TextView) findViewById(R.id.userUpdateUstId)).getText().toString();                        Address updateAddress = user.getRestaurant().getAddress();                        updateAddress.setAddressStreet(street);                        updateAddress.setAddressZipCode(zipCode);                        updateAddress.setAddressCity(city);                        Restaurant updateRestaurant = user.getRestaurant();                        updateRestaurant.setRestaurantName(restaurantName);                        updateRestaurant.setTaxNumber(ustId);                        user.setEmail(email);                        user.setFirstName(firstName);                        user.setLastName(lastName);                        if (curUser.checkWhetherAdmin()){                            final Boolean isActive = ((Switch) findViewById(R.id.isActiveSwitch)).isChecked();                            user.setIsActive(isActive);                        }                        userManager.updateUser(user);                        if (curUser.checkWhetherAdmin()){                            Intent intent = new Intent(v.getContext(), UserListActivity.class);                            v.getContext().startActivity(intent);                        } else {                            Intent intent = new Intent(v.getContext(), OverviewActivity.class);                            v.getContext().startActivity(intent);                        }                    }                });                final Button deleteButton = findViewById(R.id.userUpdateDeleteButton);                deleteButton.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(final View v) {                        if (curUser.checkWhetherAdmin() || curUser.getEmail() == user.getEmail()){                            AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailActivity.this);                            builder.setMessage(R.string.dialog_delete_user)                                    .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {                                        public void onClick(DialogInterface dialog, int id) {                                            userManager.deleteUser(user.getEmail());                                            Intent intent = new Intent(v.getContext(), UserListActivity.class);                                            v.getContext().startActivity(intent);                                        }                                    })                                    .setNegativeButton(R.string.button_cancel, null).show();                        } else {                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_permition), Toast.LENGTH_LONG);                            toast.show();                        }                    }                });            }        });    }}