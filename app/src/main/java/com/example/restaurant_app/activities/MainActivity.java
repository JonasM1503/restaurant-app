package com.example.restaurant_app.activities;import android.content.Intent;import android.graphics.Bitmap;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.ImageView;import androidx.appcompat.app.AppCompatActivity;import com.example.restaurant_app.firestore.FoodFirestoreManager;import com.example.restaurant_app.firestore.RestaurantFirestoreManager;import com.example.restaurant_app.firestore.UserFirestoreManager;import com.google.zxing.BarcodeFormat;import com.journeyapps.barcodescanner.BarcodeEncoder;/** * * @author Simon Rothmann, Jonas Mitschke * @content activity for start */public class MainActivity extends AppCompatActivity {    private FoodFirestoreManager foodFirestoreManager;    private RestaurantFirestoreManager resManager;    private UserFirestoreManager userManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        final Button button = findViewById(R.id.registerButton);        button.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);                v.getContext().startActivity(intent);            }        });        final Button loginButton = findViewById(R.id.loginButton);        loginButton.setOnClickListener(new View.OnClickListener() {            public void onClick(View v) {                Intent intent = new Intent(v.getContext(), LoginActivity.class);                v.getContext().startActivity(intent);            }        });        final Button qrTestButton = findViewById(R.id.qrButton);        qrTestButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                try {                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();                    Bitmap bitmap = barcodeEncoder.encodeBitmap("Test", BarcodeFormat.QR_CODE, 200, 200);                    ImageView imageViewQrCode = findViewById(R.id.qrCode);                    imageViewQrCode.setImageBitmap(bitmap);                } catch(Exception e) {                }            }        });    }}