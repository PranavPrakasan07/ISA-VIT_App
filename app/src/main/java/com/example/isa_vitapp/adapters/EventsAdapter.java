package com.example.isa_vitapp.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    public Map<String, Object> mDataset;
    public ArrayList<String> link_list, title_list, content_list, reg_list, youtube_list;
    public ArrayList<Boolean> open_list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView poster;
        public NeumorphCardView detail_card;
        public TextView title, content;
        public ImageButton youtube_button;
        public NeumorphButton register_button;

        public EventsViewHolder(View v) {
            super(v);
            poster = v.findViewById(R.id.event_poster);
            detail_card = v.findViewById(R.id.details_card);
            title = v.findViewById(R.id.event_name);
            content = v.findViewById(R.id.detail);
            register_button = v.findViewById(R.id.neumorphButton);
            youtube_button = v.findViewById(R.id.youtube_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(ArrayList<String> myDataset, ArrayList<String> titles, ArrayList<String> contents, ArrayList<Boolean> open, ArrayList<String> reg, ArrayList<String> youtube) {
//        mDataset.putAll(myDataset);
        link_list = myDataset;
        title_list = titles;
        content_list = contents;
        open_list = open;
        reg_list = reg;
        youtube_list = youtube;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public EventsAdapter.EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events_layout, parent, false);

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

                //click poster
//                flip_animation(holder, v, -1);

                int flag = 1;

                float scale = v.getContext().getResources().getDisplayMetrics().density;
                float distance = holder.poster.getCameraDistance() * (scale + (scale / 3));
                holder.poster.setCameraDistance(distance);
// first quarter turn
                holder.poster.animate().withLayer()
                        .rotationY(90*flag)
                        .setDuration(300)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        // second quarter turn
                                        v.setRotationY(-90*flag);
                                        v.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(300)
                                                .start();

                                        holder.poster.setVisibility(View.INVISIBLE);
                                        holder.detail_card.setVisibility(View.VISIBLE);

                                    }
                                }
                        ).start();

                holder.youtube_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!youtube_list.get(position).equals("-")) {

                            holder.youtube_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = youtube_list.get(position);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    v.getContext().startActivity(i);
                                }
                            });

                        } else {

                            String url = "https://www.youtube.com/channel/UCS-HWnmvs5cYEplDuxfO7PA";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            v.getContext().startActivity(i);
                        }
                    }
                });

                if (open_list.get(position)) {
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

                } else {
                    holder.register_button.setShapeType(ShapeType.PRESSED);
                }
            }
        });

        holder.detail_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "No touch");
//
//                holder.detail_card.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.detail_card, "scaleX", 1f, 0f);
//                        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.detail_card, "scaleX", 0f, 1f);
//                        oa1.setInterpolator(new DecelerateInterpolator());
//                        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
//                        oa1.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                holder.poster.setVisibility(View.VISIBLE);
//                                oa2.start();
//                            }
//                        });
//                        oa1.start();
//
//
//
//                    }
//                });


                int flag = -1;

                float scale = v.getContext().getResources().getDisplayMetrics().density;
                float distance = holder.detail_card.getCameraDistance() * (scale + (scale / 3));
                holder.detail_card.setCameraDistance(distance);
// first quarter turn
                holder.detail_card.animate().withLayer()
                        .rotationY(90*flag)
                        .setDuration(300)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        // second quarter turn
                                        v.setRotationY(-90*flag);
                                        v.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(300)
                                                .start();

                                        holder.poster.setVisibility(View.VISIBLE);
                                        holder.detail_card.setVisibility(View.INVISIBLE);
                                    }
                                }
                        ).start();
            }
        });

    }

    private void flip_animation(EventsViewHolder holder, View v, int flag) {

        float scale = v.getContext().getResources().getDisplayMetrics().density;
        float distance = holder.poster.getCameraDistance() * (scale + (scale / 3));
        holder.poster.setCameraDistance(distance);
// first quarter turn
        holder.poster.animate().withLayer()
                .rotationY(90*flag)
                .setDuration(300)
                .withEndAction(
                        new Runnable() {
                            @Override
                            public void run() {

                                // second quarter turn
                                v.setRotationY(-90*flag);
                                v.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(300)
                                        .start();

                                if(flag == 1) {
                                    holder.poster.setVisibility(View.VISIBLE);
                                    holder.detail_card.setVisibility(View.VISIBLE);

                                }else{
                                    holder.poster.setVisibility(View.INVISIBLE);
                                    holder.detail_card.setVisibility(View.VISIBLE);

                                }

                            }
                        }
                ).start();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return link_list.size();
    }

}
