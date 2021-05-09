package com.example.isa_vitapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.activity.EventsActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    public Map<String, Object> mDataset;
    public ArrayList<String> link_list, title_list, content_list, reg_list;
    public ArrayList<Boolean> open_list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView poster;
        public NeumorphCardView detail_card;
        public TextView title, content;
        public NeumorphButton register_button;

        public EventsViewHolder(View v) {
            super(v);
            poster = v.findViewById(R.id.event_poster);
            detail_card = v.findViewById(R.id.details_card);
            title = v.findViewById(R.id.event_name);
            content = v.findViewById(R.id.detail);
            register_button = v.findViewById(R.id.neumorphButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(ArrayList<String> myDataset, ArrayList<String> titles, ArrayList<String> contents, ArrayList<Boolean> open, ArrayList<String> reg) {
//        mDataset.putAll(myDataset);
        link_list = myDataset;
        title_list = titles;
        content_list = contents;
        open_list = open;
        reg_list = reg;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public EventsAdapter.EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_layout, parent, false);

        return new EventsViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull EventsViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.poster.setText(mDataset.get(position));

        try {
            Picasso.get()
                    .load(link_list.get(position))
                    .into(holder.poster);
        } catch (Exception e) {
            Log.d("TAG", "No photo");
            e.printStackTrace();
        }

        holder.title.setText(title_list.get(position));
        holder.content.setText(content_list.get(position));

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "No touch");
                holder.poster.setVisibility(View.INVISIBLE);
                holder.detail_card.setVisibility(View.VISIBLE);

                if(open_list.get(position)){
                    holder.register_button.setTextColor(Color.parseColor("#0C97E8"));

                    holder.register_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = reg_list.get(position);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            v.getContext().startActivity(i);
                        }
                    });


                }
                else{
                    holder.register_button.setShapeType(ShapeType.PRESSED);
                }
            }
        });

        holder.detail_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "No touch");
                holder.poster.setVisibility(View.VISIBLE);
                holder.detail_card.setVisibility(View.INVISIBLE);

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return link_list.size();
    }

}
