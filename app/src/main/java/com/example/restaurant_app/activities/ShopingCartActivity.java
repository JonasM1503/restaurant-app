package com.example.restaurant_app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restaurant_app.adapters.SharedPreferencesAdapter;
import com.example.restaurant_app.adapters.ShopingCartAdapter;
import com.example.restaurant_app.firestore.OrderFirestoreManager;
import com.example.restaurant_app.firestore.TableFirestoreManager;
import com.example.restaurant_app.firestore.UserFirestoreManager;
import com.example.restaurant_app.helpers.MailSender;
import com.example.restaurant_app.models.Order;
import com.example.restaurant_app.models.OrderPos;
import com.example.restaurant_app.models.Table;
import com.example.restaurant_app.models.User;
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

    private String msg_text;
    private OrderFirestoreManager orderFirestoreManager;
    private TableFirestoreManager tableFirestoreManager;
    private UserFirestoreManager userFirestoreManager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.shopingcart_page);

       orderFirestoreManager = OrderFirestoreManager.newInstance();
       tableFirestoreManager = TableFirestoreManager.newInstance();
       userFirestoreManager = UserFirestoreManager.newInstance();

       Gson gson = new Gson();
       Context context = getBaseContext();
       Type listType = new TypeToken<ArrayList<OrderPos>>(){}.getType();
       List<OrderPos> orderPosList = gson.fromJson(String.valueOf(SharedPreferencesAdapter.getListDefaults("orderPosList", context)), listType);
       final String tableID = SharedPreferencesAdapter.getDefaults("currentTable", context);

       ListView shopingCartListView = findViewById(R.id.shopingCartList);
       ShopingCartAdapter shopingCartAdapter = new ShopingCartAdapter(ShopingCartActivity.this, orderPosList);
       shopingCartListView.setAdapter(shopingCartAdapter);

       Button sendOrder = findViewById(R.id.shopingCartButton);
       sendOrder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               msg_text = getResources().getString(R.string.email_beginning) + "\n\n";

               for (int i=0; i<orderPosList.size(); i++) {
                   msg_text += "-----" + "\n";

                   if (orderPosList.get(i).getDrink() != null) {
                       msg_text += getResources().getString(R.string.email_item) + " " + orderPosList.get(i).getDrink().getName() + "\n";
                   } else if (orderPosList.get(i).getFood() != null) {
                       msg_text += getResources().getString(R.string.email_item) + " " + orderPosList.get(i).getFood().getName() + "\n";
                   }

                   msg_text += getResources().getString(R.string.email_quantity) + " " + orderPosList.get(i).getQuantity() + "\n";

                   if (orderPosList.get(i).getWish().equals("")) {
                       msg_text += getResources().getString(R.string.email_wish) + " -\n";
                   } else {
                       msg_text += getResources().getString(R.string.email_wish) + " " + orderPosList.get(i).getWish() + "\n";
                   }
               }

               tableFirestoreManager.getTableById(tableID, new TableFirestoreManager.GetTableByIdCallback() {
                   @Override
                   public void onCallback(Table table) {
                       userFirestoreManager.getUserByRestaurantId(table.getRestaurantId(), new UserFirestoreManager.GetUserByRestaurantCallback() {
                           @Override
                           public void onCallback(User user) {
                               try {
                                   MailSender sender = new MailSender();
                                   sender.sendMail(getResources().getString(R.string.email_subject),
                                           msg_text,
                                           "restaurant-app@jonasmitschke.com",
                                           "jmitschke2012@gmail.com" /* for live: user.getEmail() */);
                                   SharedPreferencesAdapter.setListDefaults("orderPosList", new ArrayList<String>(), context);
                               } catch (Exception e) {
                                   Log.e("SendMailError", e.getMessage(), e);
                               }

                               Order new_order = new Order(tableID, false, orderPosList);
                               orderFirestoreManager.createOrder(new_order);

                               Intent intent = new Intent(view.getContext(), MenuActivity.class);
                               view.getContext().startActivity(intent);
                               Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_email_sent), Toast.LENGTH_LONG);
                               toast.show();
                           }

                           @Override
                           public void onFailureCallback(Exception e) {
                               Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                               toast.show();
                           }
                       });
                   }

                   @Override
                   public void onFailureCallback(Exception e) {
                       Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
                       toast.show();
                   }
               });
           }
       });
   }
}
