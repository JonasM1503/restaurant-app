package com.example.restaurant_app.activities;import android.app.Activity;import android.os.Bundle;import android.widget.ListView;import androidx.annotation.NonNull;import com.example.restaurant_app.adapters.UserListAdapter;import com.example.restaurant_app.helpers.CollectionNames;import com.example.restaurant_app.models.User;import com.google.android.gms.tasks.OnCompleteListener;import com.google.android.gms.tasks.Task;import com.google.firebase.firestore.DocumentSnapshot;import com.google.firebase.firestore.FirebaseFirestore;import com.google.firebase.firestore.QuerySnapshot;import java.util.ArrayList;import java.util.List;/** * * @author Jonas Mitschke * @content activity for user-list */public class UserListActivity extends Activity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.user_list);        FirebaseFirestore.getInstance().collection(CollectionNames.userCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {            @Override            public void onComplete(@NonNull Task<QuerySnapshot> task) {                List<User> return_users = new ArrayList<>();                if(task.isSuccessful()){                    for(DocumentSnapshot document : task.getResult()) {                        User user = document.toObject(User.class);                        return_users.add(user);                    }                    ListView usersListView = (ListView) findViewById(R.id.userList);                    UserListAdapter userAdapter = new UserListAdapter(UserListActivity.this, return_users);                    usersListView.setAdapter(userAdapter);                }            }        });    }}