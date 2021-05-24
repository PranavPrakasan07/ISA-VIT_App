package com.example.isa_vitapp.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.BoardViewHolder> {
    public ArrayList<String> name_list, position_list, instagram_list, linkedin_list, photo_list;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView photo, instagram, linkedin;
        public TextView board_member_name, board_position;

        public BoardViewHolder(View v) {
            super(v);
            photo = v.findViewById(R.id.member_photo);
            board_member_name = v.findViewById(R.id.member_name);
            board_position = v.findViewById(R.id.position);
            instagram = v.findViewById(R.id.instagram_icon);
            linkedin = v.findViewById(R.id.linkedin_icon);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BoardListAdapter(ArrayList<String> name, ArrayList<String> position, ArrayList<String> insta, ArrayList<String> linkedin, ArrayList<String> photo) {
//        mDataset.putAll(myDataset);
        name_list = name;
        position_list = position;
        instagram_list = insta;
        linkedin_list = linkedin;
        photo_list = photo;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public BoardListAdapter.BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_layout, parent, false);

        return new BoardViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull BoardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.poster.setText(mDataset.get(position));

        try {
            Picasso.get()
                    .load(photo_list.get(position))
                    .into(holder.photo);
        } catch (Exception e) {
            Log.d("TAG", "No photo");
            e.printStackTrace();
        }

        holder.board_member_name.setText(name_list.get(position));
        holder.board_position.setText(position_list.get(position));

        holder.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(instagram_list.get(position)); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        });

        holder.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(linkedin_list.get(position)); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name_list.size();
    }

}