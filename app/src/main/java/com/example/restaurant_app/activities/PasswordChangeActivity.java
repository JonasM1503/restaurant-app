package com.example.restaurant_app.activities;import android.app.Activity;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.TextView;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.UserFirestoreManager;import com.example.restaurant_app.models.User;import com.google.gson.Gson;/** * * @author Jonas Mitschke * @content activity for password change */public class PasswordChangeActivity extends Activity {    private UserFirestoreManager userManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.user_detail);        userManager = UserFirestoreManager.newInstance();        Context context = getBaseContext();        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        final Button saveButton = findViewById(R.id.passwordUpdateSaveButton);        saveButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                final String passwordOld = ((TextView) findViewById(R.id.pwUpdateOldPWText)).getText().toString();                final String passwordNew = ((TextView) findViewById(R.id.pwUpdateNewPWText)).getText().toString();                User updateUser = curUser;                if (curUser.getPassword().compareTo(User.hashPassword(passwordOld)) != 0) {                    updateUser.setPassword(User.hashPassword(passwordNew));                    userManager.updateUser(updateUser);                }                // error handling missing                Intent intent = new Intent(v.getContext(), UserListActivity.class);                v.getContext().startActivity(intent);            }        });    }}