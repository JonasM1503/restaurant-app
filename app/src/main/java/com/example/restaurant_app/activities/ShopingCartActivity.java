package com.example.restaurant_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.adapters.ShopingCartAdapter;
import com.example.restaurant_app.models.OrderPos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Simon Rothmann
 * @content activity for shoppingCart-list
 */
public class ShopingCartActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.shopingcart_page);

       Gson gson = new Gson();
       Context context = getBaseContext();
       Type listType = new TypeToken<ArrayList<OrderPos>>(){}.getType();
       List<OrderPos> orderPosList = gson.fromJson(String.valueOf(SharedPreferencesAdapter.getListDefaults("orderPosList", context)), listType);

       ListView shopingCartListView = findViewById(R.id.shopingCartList);
       ShopingCartAdapter shopingCartAdapter = new ShopingCartAdapter(ShopingCartActivity.this, orderPosList);
       shopingCartListView.setAdapter(shopingCartAdapter);

       Button sendOrder = findViewById(R.id.shopingCartButton);
       sendOrder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferencesAdapter.setListDefaults("orderPosList", new ArrayList<String>(), context);
               Intent intent = new Intent(view.getContext(), MenuActivity.class);
               view.getContext().startActivity(intent);
           }
       });

   }

}
