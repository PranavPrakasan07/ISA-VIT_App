package com.example.isa_vitapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.classes.MemberData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> search_content = new ArrayList<>();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_, container, false);

        EditText searchbar = view.findViewById(R.id.search_bar);
        ImageButton searchbutton = view.findViewById(R.id.search_button);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = searchbar.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Board_Member_Data")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                        Log.d("TAG", document.getId() + " => " + document.getData());

                                        MemberData memberData = document.toObject(MemberData.class);

                                        Log.d("TAGDATA", memberData.getVit_email());
                                        Log.d("TAGDATA", memberData.getName());

                                        if(memberData.getName().contains(name) | memberData.getName().equals(name)){
                                            Log.d("TAGDATA", memberData.getName());
                                            Toast.makeText(getActivity(), memberData.getName(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });

//                db.collection("Core_Member_Data")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                        Log.d("TAG", document.getId() + " => " + document.getData());
//
//                                        MemberData memberData = document.toObject(MemberData.class);
//
//                                        Log.d("TAGDATA", memberData.getVit_email());
//                                        Log.d("TAGDATA", memberData.getName());
//
//                                    }
//                                } else {
//                                    Log.d("TAG", "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });

            }
        });

        return view;

    }
}