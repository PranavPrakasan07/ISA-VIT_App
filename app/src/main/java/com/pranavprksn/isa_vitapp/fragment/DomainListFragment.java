package com.pranavprksn.isa_vitapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pranavprksn.isa_vitapp.R;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DomainListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DomainListFragment extends Fragment {

    public static String domain_selected = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DomainListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DomainListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DomainListFragment newInstance(String param1, String param2) {
        DomainListFragment fragment = new DomainListFragment();
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
        View view = inflater.inflate(R.layout.fragment_domain_list, container, false);

        domain_selected = "";

        NeumorphCardView app_dev = view.findViewById(R.id.n_app_dev_card);
        NeumorphCardView web_dev = view.findViewById(R.id.n_web_card);
        NeumorphCardView iot = view.findViewById(R.id.n_iot_card);
        NeumorphCardView aiml = view.findViewById(R.id.n_aiml_card);
        NeumorphCardView design = view.findViewById(R.id.n_design_card);
        NeumorphCardView cno = view.findViewById(R.id.n_content_card);
        NeumorphCardView enf = view.findViewById(R.id.n_ef_card);

        app_dev.setOnClickListener(v -> {
            domain_selected = "App Dev";
            app_dev.setShapeType(ShapeType.PRESSED);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        web_dev.setOnClickListener(v -> {
            domain_selected = "Web Dev";
            web_dev.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        aiml.setOnClickListener(v -> {
            domain_selected = "AIML";
            aiml.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        iot.setOnClickListener(v -> {
            domain_selected = "IoT";
            iot.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        design.setOnClickListener(v -> {
            domain_selected = "Design";
            design.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        enf.setOnClickListener(v -> {
            domain_selected = "E&F";
            enf.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });

        cno.setOnClickListener(v -> {
            domain_selected = "C&O";
            cno.setShapeType(ShapeType.PRESSED);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new CreateTaskFragment()).commit();
        });


        return view;
    }
}