package com.example.isa_vitapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isa_vitapp.R;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Task_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Task_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String domain_selected = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Task_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Task_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Task_Fragment newInstance(String param1, String param2) {
        Task_Fragment fragment = new Task_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_task_, container, false);

        NeumorphCardView app = view.findViewById(R.id.n_app);
        NeumorphCardView web = view.findViewById(R.id.n_web);
        NeumorphCardView iot = view.findViewById(R.id.n_iot);
        NeumorphCardView aiml = view.findViewById(R.id.n_aiml);
        NeumorphCardView design = view.findViewById(R.id.n_design);
        NeumorphCardView cno = view.findViewById(R.id.n_content);
        NeumorphCardView enf = view.findViewById(R.id.n_finance);

        app.setOnClickListener(v -> {
            app.setShapeType(ShapeType.PRESSED);
            domain_selected = "App Dev";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            app.setShapeType(ShapeType.FLAT);

        });

        web.setOnClickListener(v -> {
            web.setShapeType(ShapeType.PRESSED);
            domain_selected = "Web Dev";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            web.setShapeType(ShapeType.FLAT);

        });

        aiml.setOnClickListener(v -> {
            aiml.setShapeType(ShapeType.PRESSED);
            domain_selected = "AIML";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            aiml.setShapeType(ShapeType.FLAT);

        });

        iot.setOnClickListener(v -> {
            iot.setShapeType(ShapeType.PRESSED);
            domain_selected = "IoT";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            iot.setShapeType(ShapeType.FLAT);

        });

        design.setOnClickListener(v -> {
            design.setShapeType(ShapeType.PRESSED);
            domain_selected = "Design";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            design.setShapeType(ShapeType.FLAT);

        });

        cno.setOnClickListener(v -> {
            cno.setShapeType(ShapeType.PRESSED);
            domain_selected = "C&O";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            cno.setShapeType(ShapeType.FLAT);

        });

        enf.setOnClickListener(v -> {
            enf.setShapeType(ShapeType.PRESSED);
            domain_selected = "E&F";
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit();
            enf.setShapeType(ShapeType.FLAT);

        });

        return view;
    }
}