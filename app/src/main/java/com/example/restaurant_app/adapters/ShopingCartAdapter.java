package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.activities.ShopingCartActivity;
import com.example.restaurant_app.models.OrderPos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Simon Rothmann, Jonas Mitschke
 * @content adapter for shoppingCart-List
 */
public class ShopingCartAdapter extends ArrayAdapter<OrderPos> {


    public ShopingCartAdapter(Context context, List<OrderPos> object) {
        super(context, 0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_shoppingcart, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.shopingItemName);
        TextView priceTextView = convertView.findViewById(R.id.shopingItemPrice);
        TextView quantityTextview = convertView.findViewById(R.id.shopingCartQuantity);
        ImageButton deleteItemButton = convertView.findViewById(R.id.deleteItemButton);

        final OrderPos orderPos = getItem(position);

        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<OrderPos>>(){}.getType();
                List<OrderPos> orderPosList = gson.fromJson(String.valueOf(SharedPreferencesAdapter.getListDefaults("orderPosList", view.getContext())), listType);

                String remove_uuid = orderPos.getOrderPosUUID();
                orderPosList.removeIf(obj -> obj.getOrderPosUUID().equals(remove_uuid));

                List<String> orderPosStringListNew = new ArrayList<>();
                for (int i=0; i<orderPosList.size(); i++) {
                    orderPosStringListNew.add(gson.toJson(orderPosList.get(i)));
                }
                SharedPreferencesAdapter.setListDefaults("orderPosList", orderPosStringListNew, view.getContext());

                Intent intent = new Intent(view.getContext(), ShopingCartActivity.class);
                view.getContext().startActivity(intent);
                Toast toast = Toast.makeText(view.getContext().getApplicationContext(), view.getContext().getResources().getString(R.string.toast_orderPos_removed), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        if (orderPos.getDrink() != null) {
            nameTextView.setText(orderPos.getDrink().getName());
            priceTextView.setText(orderPos.getDrink().getPrice().toString() + " €");
            quantityTextview.setText(Integer.toString(orderPos.getQuantity()));
        } else if (orderPos.getFood() != null) {
            nameTextView.setText(orderPos.getFood().getName());
            priceTextView.setText(orderPos.getFood().getPrice().toString() + " €");
            quantityTextview.setText(Integer.toString(orderPos.getQuantity()));
        }
        return convertView;
    }
}
