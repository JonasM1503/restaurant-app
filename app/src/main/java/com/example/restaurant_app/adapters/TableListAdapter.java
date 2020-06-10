package com.example.restaurant_app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurant_app.activities.R;
import com.example.restaurant_app.activities.TableDetailActivity;
import com.example.restaurant_app.models.Table;

import java.util.List;

/**
 *
 * @author Jonas Mitschke
 * @content adapter for table data to listview-item
 */
public class TableListAdapter extends ArrayAdapter<Table> {
    public TableListAdapter(Context context, List<Table> object){
        super(context,0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_tables,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.table_number);
        Button viewTableButton = convertView.findViewById(R.id.viewTableButton);

        final Table table = getItem(position);

        viewTableButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TableDetailActivity.class);
                intent.putExtra("tableID", table.getTableId());
                v.getContext().startActivity(intent);
            }
        });

        nameTextView.setText(table.getTableNumber());

        return convertView;
    }
}