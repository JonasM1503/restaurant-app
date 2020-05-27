package com.example.restaurant_app.activities;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;import com.example.restaurant_app.adapters.SharedPreferencesAdapter;import com.google.zxing.integration.android.IntentIntegrator;import com.google.zxing.integration.android.IntentResult;import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;/** * * @author Simon Rothmann, Jonas Mitschke * @content activity for start */public class MainActivity extends AppCompatActivity {    Button btnScan;    TextView tv_qr_readTxt;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        // logout current user / reset current table        SharedPreferencesAdapter.setDefaults("currentUser", null, this);        SharedPreferencesAdapter.setDefaults("currentTable", null, this);        btnScan = findViewById(R.id.btnScan);        tv_qr_readTxt = findViewById(R.id.tv_qr_readTxt);        btnScan.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);                integrator.setPrompt(getResources().getString(R.string.message_scan));                integrator.setCameraId(0);                integrator.setOrientationLocked(false);                integrator.setBeepEnabled(false);                integrator.setBarcodeImageEnabled(false);                integrator.initiateScan();            }        });        final Button button = findViewById(R.id.registerButton);        button.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);                v.getContext().startActivity(intent);            }        });        final Button loginButton = findViewById(R.id.loginButton);        loginButton.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), LoginActivity.class);                v.getContext().startActivity(intent);            }        });    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);        if(result != null) {            if(result.getContents() != null) {                SharedPreferencesAdapter.setDefaults("currentTable", result.getContents(), getBaseContext());                Intent intent = new Intent(MainActivity.this, MenuActivity.class);                startActivityForResult(intent, REQUEST_CODE);            }        } else {            // This is important, otherwise the result will not be passed to the fragment            super.onActivityResult(requestCode, resultCode, data);        }    }}