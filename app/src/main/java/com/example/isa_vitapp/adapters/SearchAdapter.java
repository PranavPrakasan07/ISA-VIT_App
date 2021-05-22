package com.example.isa_vitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isa_vitapp.R;

public class SearchAdapter extends BaseAdapter {

    Context context;
    String[] name_list;
    LayoutInflater inflter;

    public SearchAdapter(Context applicationContext, String[] names) {
        this.name_list = names;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return name_list.length;
    }

    @Override
    public Object getItem(int i) {
        Toast.makeText(context, name_list[i], Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.search_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.member_name);
        name.setText(name_list[position]);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, name_list[position], Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
