package com.pranavprksn.isa_vitapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pranavprksn.isa_vitapp.R;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Fragment extends Fragment {

    NeumorphCardView add_task, remove_task;

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



        add_task = view.findViewById(R.id.n_add_task_card);
        remove_task = view.findViewById(R.id.n_del_task_card);

        add_task.setOnClickListener(v -> {

            add_task.setShapeType(ShapeType.PRESSED);

            Fragment domainList = new DomainListFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, domainList).commit();
        });

//        remove_task.setOnClickListener(v -> {
//
//            remove_task.setShapeType(ShapeType.PRESSED);
//
//            Toast.makeText(getActivity(), "Button remove clicked", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getActivity(), RemoveTaskActivity.class));
//        });

        return view;
    }


}