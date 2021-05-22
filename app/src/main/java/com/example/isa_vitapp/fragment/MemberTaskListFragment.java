package com.example.isa_vitapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.adapters.DomainMembersAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberTaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberTaskListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MemberTaskListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberTaskListFragment newInstance(String param1, String param2) {
        MemberTaskListFragment fragment = new MemberTaskListFragment();
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
        View view = inflater.inflate(R.layout.fragment_member_task_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("Task_" + domain_name)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                            Log.d("TAG", document.getId() + " => " + document.getData());
//
//                            details = (document.getData());
//
//                            name_list.add((String) details.get("name"));
//
//                            try {
//                                Log.d("TAG", "Reached here" + details.toString());
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                                recyclerView.setAdapter(new DomainMembersAdapter(name_list));
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    } else {
//                        Log.d("TAG", "Error getting documents: ", task.getException());
//                    }
//                });



        return view;
    }
}