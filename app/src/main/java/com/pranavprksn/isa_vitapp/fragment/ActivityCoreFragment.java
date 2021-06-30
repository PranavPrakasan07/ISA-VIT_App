package com.pranavprksn.isa_vitapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.activity.HomeCoreActivity;

import soup.neumorphism.NeumorphCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityCoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityCoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String selected_domain = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivityCoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityCoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityCoreFragment newInstance(String param1, String param2) {
        ActivityCoreFragment fragment = new ActivityCoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity_core, container, false);

        NeumorphCardView domain1_card = view.findViewById(R.id.n_domain1);
        NeumorphCardView domain2_card = view.findViewById(R.id.n_domain2);

        TextView domain1 = view.findViewById(R.id.textView0);
        TextView domain2 = view.findViewById(R.id.textView1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                domain1.setText(HomeCoreActivity.domain1);
                domain2.setText(HomeCoreActivity.domain2);
            }
        }, 500);


        domain1_card.setOnClickListener(v -> {
            selected_domain = HomeCoreActivity.domain1;
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MyTaskListFragment()).commit();
        });

        domain2_card.setOnClickListener(v -> {
            selected_domain = HomeCoreActivity.domain2;
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MyTaskListFragment()).commit();
        });


        return view;
    }
}