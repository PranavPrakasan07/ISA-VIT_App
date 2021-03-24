package com.example.isa_vitapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.isa_vitapp.AddTaskActivity;
import com.example.isa_vitapp.AddTaskFragment;
import com.example.isa_vitapp.R;
import com.example.isa_vitapp.RemoveTaskActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Fragment extends Fragment {

    CardView add_task_button;
    CardView remove_task_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_Fragment newInstance(String param1, String param2) {
        Add_Fragment fragment = new Add_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_, container, false);

        add_task_button = view.findViewById(R.id.add_task_cardView);
        remove_task_button = view.findViewById(R.id.del_task_cardView);

        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GG", "Add Task Clicked");
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
            }

        });

        remove_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button remove clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), RemoveTaskActivity.class));
            }
        });

        return view;
    }
}