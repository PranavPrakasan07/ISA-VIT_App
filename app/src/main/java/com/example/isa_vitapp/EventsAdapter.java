package com.example.isa_vitapp;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import soup.neumorphism.NeumorphCardView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    public Map<String, Object> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView poster;
        public NeumorphCardView detail_card;

        public EventsViewHolder(View v) {
            super(v);
            poster = v.findViewById(R.id.event_poster);
            detail_card = v.findViewById(R.id.details_card);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(Map<String, Object> myDataset) {
        mDataset.putAll(myDataset);
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
                    .load((Uri) mDataset.get("poster"))
                    .into(holder.poster);
        } catch (Exception e) {
            Log.d("TAG", "No photo");
            e.printStackTrace();
        }

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "No touch");
                holder.poster.setVisibility(View.INVISIBLE);
                holder.detail_card.setVisibility(View.VISIBLE);

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
