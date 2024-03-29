package com.pranavprksn.isa_vitapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.activity.Home;
import com.pranavprksn.isa_vitapp.adapters.SearchHistoryAdapter;
import com.pranavprksn.isa_vitapp.classes.MemberData;
import com.pranavprksn.isa_vitapp.classes.OnSwipeTouchListener;

import java.util.Objects;

import soup.neumorphism.NeumorphCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    NeumorphCardView search_history_card;

    public static String searched_member_email = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Fragment newInstance(String param1, String param2) {
        Search_Fragment fragment = new Search_Fragment();
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
    public void onStart() {
        super.onStart();

        if (Home.history_list.size() == 0){
            search_history_card.setVisibility(View.INVISIBLE);
        }else{
            search_history_card.setVisibility(View.VISIBLE);
        }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new SearchHistoryAdapter(Home.history_list, Home.vit_email_list));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_, container, false);

        AutoCompleteTextView searchbar = view.findViewById(R.id.search_bar);
        ImageButton searchbutton = view.findViewById(R.id.search_button);

        ConstraintLayout fragment_search_layout = view.findViewById(R.id.fragment_search_layout);

        String[] member_list = getResources().getStringArray(R.array.member_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, member_list);
        //Getting the instance of AutoCompleteTextView
        searchbar.setThreshold(1);//will start working from first character
        searchbar.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        searchbar.setTextColor(getResources().getColor(R.color.blue_button_light));

        fragment_search_layout.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                Fragment domainList = new Add_Fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, domainList).commit();
                Home.chipNavigationBar.setItemSelected(R.id.nav_new_add, true);
            }
            public void onSwipeLeft() {
                Fragment domainList = new Task_Fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, domainList).commit();
                Home.chipNavigationBar.setItemSelected(R.id.nav_task, true);
            }
            public void onSwipeBottom() {
            }
        });

        search_history_card = view.findViewById(R.id.search_history_card);

        recyclerView = view.findViewById(R.id.list_view);

        searchbutton.setOnClickListener(v -> {
            String name = searchbar.getText().toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("Board_Member_Data")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                MemberData memberData = document.toObject(MemberData.class);

                                Log.d("TAGDATA", memberData.getVit_email());
                                Log.d("TAGDATA", memberData.getName());

                                if (memberData.getName().toLowerCase().equals(name.toLowerCase())) {
                                    Log.d("TAGDATA", memberData.getName());

                                    if (Home.history_list.size() > 4) {
                                        Home.history_list.set(Home.counter % 5, memberData.getName());
                                        Home.vit_email_list.set(Home.counter % 5, memberData.getVit_email());

                                    } else {
                                        Home.history_list.add(memberData.getName());
                                        Home.vit_email_list.add(memberData.getVit_email());
                                    }

                                    if (Home.history_list.size() == 0){
                                        search_history_card.setVisibility(View.INVISIBLE);
                                    }else{
                                        search_history_card.setVisibility(View.VISIBLE);
                                    }

                                    Home.counter++;

                                    try {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                        recyclerView.setAdapter(new SearchHistoryAdapter(Home.history_list, Home.vit_email_list));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    searched_member_email = memberData.getVit_email();
                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new SearchMemberFragment()).commit();
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    });

            db.collection("Core_Member_Data")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                MemberData memberData = document.toObject(MemberData.class);

                                Log.d("TAGDATA", memberData.getVit_email());
                                Log.d("TAGDATA", memberData.getName());

                                if (memberData.getName().toLowerCase().equals(name.toLowerCase())) {
                                    Log.d("TAGDATA", memberData.getName());

                                    if (Home.history_list.size() > 4) {
                                        Home.history_list.set(Home.counter % 5, memberData.getName());
                                        Home.vit_email_list.set(Home.counter % 5, memberData.getVit_email());
                                    } else {
                                        Home.history_list.add(memberData.getName());
                                        Home.vit_email_list.add(memberData.getVit_email());
                                    }

                                    if (Home.history_list.size() == 0){
                                        search_history_card.setVisibility(View.INVISIBLE);
                                    }else{
                                        search_history_card.setVisibility(View.VISIBLE);
                                    }

                                    Home.counter++;

                                    try {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                        recyclerView.setAdapter(new SearchHistoryAdapter(Home.history_list, Home.vit_email_list));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    searched_member_email = memberData.getVit_email();
                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new SearchMemberFragment()).commit();

                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    });
        });

        return view;

    }
}