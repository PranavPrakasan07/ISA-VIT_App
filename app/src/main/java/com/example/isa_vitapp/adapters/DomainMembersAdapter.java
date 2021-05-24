package com.example.isa_vitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.fragment.MemberListFragment;
import com.example.isa_vitapp.fragment.MemberTaskListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DomainMembersAdapter extends RecyclerView.Adapter<DomainMembersAdapter.DomainMembersViewHolder> {
    public ArrayList<String> name_list;
    public ArrayList<String> email_list;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class DomainMembersViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView member_name;
        public ImageButton expand_button;


        public DomainMembersViewHolder(View v) {
            super(v);

            member_name = v.findViewById(R.id.member_name);
            expand_button = v.findViewById(R.id.expand_button);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DomainMembersAdapter(ArrayList<String> names, ArrayList<String> emails) {
//        mDataset.putAll(myDataset);

        name_list = names;
        email_list = emails;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public DomainMembersAdapter.DomainMembersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list_item, parent, false);

        return new DomainMembersViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull DomainMembersViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.poster.setText(mDataset.get(position));

        holder.member_name.setText(name_list.get(position));

        holder.member_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), name_list.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(), email_list.get(position), Toast.LENGTH_SHORT).show();

                MemberListFragment.selected_core_member_name = name_list.get(position);
                MemberListFragment.selected_core_member_email = email_list.get(position);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new MemberTaskListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myFragment).addToBackStack(null).commit();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name_list.size();
    }

}
