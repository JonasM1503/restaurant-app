package com.example.restaurant_app.activities;import android.annotation.SuppressLint;import android.app.Activity;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.TextView;import android.widget.Toast;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.example.restaurant_app.firestore.TableFirestoreManager;import com.example.restaurant_app.helpers.GenerateQRCode;import com.example.restaurant_app.models.Table;import com.example.restaurant_app.models.User;import com.google.gson.Gson;/** * * @author Jonas Mitschke * @content activity for table-detail */public class TableDetailActivity extends Activity {    private TableFirestoreManager tableManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.table_detail);        tableManager = TableFirestoreManager.newInstance();        final String tableID = getIntent().getExtras().getString("tableID");        Context context = getBaseContext();        Gson gson = new Gson();        final User curUser = gson.fromJson(SharedPreferencesAdapter.getDefaults("currentUser", context), User.class);        if (tableID == null) throw new AssertionError();        if(!tableID.equals("0")) {            tableManager.getTableById(tableID, new TableFirestoreManager.GetTableByIdCallback() {                @SuppressLint("SetTextI18n")                @Override                public void onCallback(final Table table) {                    ((TextView) findViewById(R.id.tableUpdateNo)).setText(table.getTableNumber().toString());                    final Button saveButton = findViewById(R.id.tableUpdateSaveButton);                    saveButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(View v) {                            final Integer no = Integer.parseInt(((TextView) findViewById(R.id.tableUpdateNo)).getText().toString());                            table.setTableNumber(no);                            tableManager.updateTable(table);                            Intent intent = new Intent(v.getContext(), TableListActivity.class);                            v.getContext().startActivity(intent);                        }                    });                    final Button genQRButton = findViewById(R.id.tableUpdateGenQRButton);                    genQRButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(final View view) {                            String picName = "QR-Code-" + curUser.getRestaurant().getRestaurantName().toLowerCase().replaceAll("[^a-zA-Z0-9_-]", "") + "-" + table.getTableNumber().toString();                            GenerateQRCode.generateCode(view.getContext(), tableID, picName);                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_genQR), Toast.LENGTH_LONG);                            toast.show();                        }                    });                    final Button deleteButton = findViewById(R.id.tableUpdateDeleteButton);                    deleteButton.setOnClickListener(new View.OnClickListener() {                        @Override                        public void onClick(final View v) {                            if (curUser.getRestaurant().getRestaurantId().equals(table.getRestaurantId())) {                                AlertDialog.Builder builder = new AlertDialog.Builder(TableDetailActivity.this);                                builder.setMessage(R.string.dialog_delete_table)                                        .setPositiveButton(R.string.button_confirm, new DialogInterface.OnClickListener() {                                            public void onClick(DialogInterface dialog, int id) {                                                tableManager.deleteTable(table.getTableId());                                                Intent intent = new Intent(v.getContext(), TableListActivity.class);                                                v.getContext().startActivity(intent);                                            }                                        })                                        .setNegativeButton(R.string.button_cancel, null).show();                            } else {                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_no_permition), Toast.LENGTH_LONG);                                toast.show();                            }                        }                    });                }            });        }        else {            findViewById(R.id.tableUpdateDeleteButton).setVisibility(View.INVISIBLE);            final Button saveButton = findViewById(R.id.tableUpdateSaveButton);            saveButton.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(final View view) {                    String number_help = ((TextView) findViewById(R.id.tableUpdateNo)).getText().toString();                    final Integer number = Integer.parseInt(number_help);                    final Table newTable = new Table(curUser.getRestaurant().getRestaurantId(), number);                    tableManager.createTable(newTable);                    Intent intent = new Intent(view.getContext(), TableListActivity.class);                    view.getContext().startActivity(intent);                }            });        }    }}