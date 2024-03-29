package com.pranavprksn.isa_vitapp.adapters;

import android.util.Log;
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

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.fragment.MemberTaskFragment;
import com.pranavprksn.isa_vitapp.fragment.MemberTaskListFragment;
import com.pranavprksn.isa_vitapp.fragment.MyTaskFragment;
import com.pranavprksn.isa_vitapp.fragment.MyTaskListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    public ArrayList<String> deadline_list = new ArrayList<>();
    public ArrayList<Boolean> passed_list = new ArrayList<>();
    public ArrayList<String> description_list = new ArrayList<>();
    public ArrayList<String> title_list = new ArrayList<>();
    public ArrayList<String> setby_list = new ArrayList<>();

    boolean fromMember = true;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView task_name;
        public ImageButton pass_button;


        public TaskListViewHolder(View v) {
            super(v);

            task_name = v.findViewById(R.id.task_title);
            pass_button = v.findViewById(R.id.pass_button);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskListAdapter(ArrayList<String> title, ArrayList<Boolean> passed, ArrayList<String> description, ArrayList<String> deadline, ArrayList<String> setby, boolean from) {
//        mDataset.putAll(myDataset);

        title_list = title;
        setby_list = setby;
        deadline_list = deadline;
        description_list = description;
        passed_list = passed;

        fromMember = from;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TaskListAdapter.TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false);

        return new TaskListViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull TaskListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.poster.setText(mDataset.get(position));

        holder.task_name.setText(title_list.get(position));

        if(passed_list.get(position)){
            holder.pass_button.setImageResource(R.drawable.ic_blue_round);
        }else{
            holder.pass_button.setImageResource(R.drawable.ic_grey_round);
        }

        holder.task_name.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), title_list.get(position), Toast.LENGTH_SHORT).show();
//            Toast.makeText(v.getContext(), deadline_list.get(position), Toast.LENGTH_SHORT).show();

            Log.d("TAG", title_list.get(position));
            Log.d("TAG", deadline_list.get(position));



            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            Fragment myFragment;
            if (!fromMember) {
                //For board side
                MemberTaskListFragment.task_clicked_title = title_list.get(position);
                MemberTaskListFragment.task_clicked_deadline = deadline_list.get(position);
                myFragment = new MemberTaskFragment();
            }
            else{
                // or core side
                MyTaskListFragment.task_clicked_title = title_list.get(position);
                MyTaskListFragment.task_clicked_deadline = deadline_list.get(position);
                myFragment = new MyTaskFragment();
            }
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myFragment).addToBackStack(null).commit();
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return title_list.size();
    }

}
