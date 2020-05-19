package com.example.restaurant_app.activities;import android.app.Activity;import android.content.Context;import android.os.Bundle;import android.widget.ListView;import androidx.annotation.NonNull;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.adapters.TableListAdapter;import com.example.restaurant_app.helpers.CollectionNames;import com.example.restaurant_app.models.Table;import com.example.restaurant_app.models.User;import com.google.android.gms.tasks.OnCompleteListener;import com.google.android.gms.tasks.Task;import com.google.firebase.firestore.DocumentSnapshot;import com.google.firebase.firestore.FirebaseFirestore;import com.google.firebase.firestore.QuerySnapshot;import com.google.gson.Gson;import java.util.ArrayList;import java.util.List;/** * * @author Jonas Mitschke * @content activity for table-list */public class TableListActivity extends Activity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.table_list);        Gson gson = new Gson();        Context context = getBaseContext();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser",context), User.class);        FirebaseFirestore.getInstance().collection(CollectionNames.tableCollection).whereEqualTo("restaurant", curUser.getRestaurant()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {            @Override            public void onComplete(@NonNull Task<QuerySnapshot> task) {                List<Table> return_tables = new ArrayList<>();                if(task.isSuccessful()){                    for(DocumentSnapshot document : task.getResult()) {                        Table table = document.toObject(Table.class);                        return_tables.add(table);                    }                    ListView tablesListView = (ListView) findViewById(R.id.tableList);                    TableListAdapter tableAdapter = new TableListAdapter(TableListActivity.this, return_tables);                    tablesListView.setAdapter(tableAdapter);                }            }        });    }}