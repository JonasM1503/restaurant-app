package com.example.restaurant_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.firestore.UserFirestoreManager;
import com.example.restaurant_app.models.User;
import com.google.gson.Gson;


/**
 *
 * @author Simon Rothmann, Jonas Mitschke
 * @content login process
 */
public class LoginActivity extends AppCompatActivity {
    private UserFirestoreManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Activity this_activity = this;
        userManager = UserFirestoreManager.newInstance();

        /**
         * login function, checking password and email to firebase
         */
        final Button loginFinishButton = findViewById(R.id.loginButtonFinish);
        loginFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String email = ((TextView) findViewById(R.id.loginEmail)).getText().toString();
                final String password = ((TextView) findViewById(R.id.loginPassword)).getText().toString();
                final String hashedPassword = User.hashPassword(password);
                userManager.getUserByEmail(email, new UserFirestoreManager.GetUserByEmailCallback() {
                    @Override
                    public void onCallback(User user) {
                        if(user.getPassword().compareTo(hashedPassword) != 0){
                            Toast toast = Toast.makeText(getApplicationContext(), "Benutzername oder Passwort falsch", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if(!user.getIsActive()){
                            Toast toast = Toast.makeText(
                                    getApplicationContext(),
                                    "Benutzer ist nicht aktiv, bitte den Administrator kontaktieren",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else {
                            Gson gson = new Gson();
                            String user_json = gson.toJson(user);
                            Context context = getBaseContext();
                            SharedPreferencesAdapter.setDefaults("currentUser", user_json, context);
                            Intent intent = new Intent(v.getContext(), OverviewActivity.class);
                            v.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailureCallback(Exception e) {
                        Intent intent = new Intent(this_activity, DrinkListActivity.class);
                        this_activity.startActivity(intent);
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
        }
    });
}
}
