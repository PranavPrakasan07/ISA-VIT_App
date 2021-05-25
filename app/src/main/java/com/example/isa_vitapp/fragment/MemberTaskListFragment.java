package com.example.isa_vitapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.adapters.TaskListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import soup.neumorphism.NeumorphButton;

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

    ArrayList<String> deadline_list = new ArrayList<>();
    ArrayList<Boolean> passed_list = new ArrayList<>();
    ArrayList<String> description_list = new ArrayList<>();
    ArrayList<String> title_list = new ArrayList<>();
    ArrayList<String> setby_list = new ArrayList<>();

    Map<String, Object> details = new Map<String, Object>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable @org.jetbrains.annotations.Nullable Object value) {
            return false;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object get(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object remove(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull @NotNull Map<? extends String, ?> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @NotNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Collection<Object> values() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return null;
        }
    };


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

        NeumorphButton domain = view.findViewById(R.id.neumorphButton);
        NeumorphButton member = view.findViewById(R.id.member_name);

        domain.setText(Task_Fragment.domain_selected);
        member.setText(MemberListFragment.selected_core_member_name);

        Toast.makeText(getActivity(), MemberListFragment.selected_core_member_email, Toast.LENGTH_SHORT).show();


        ImageButton back_button = view.findViewById(R.id.back_button);

        back_button.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MemberListFragment()).commit());


        db.collection("Task_" + Task_Fragment.domain_selected)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", document.getId() + " => " + document.getData());

                            details = (document.getData());

                            title_list.add((String) details.get("title"));
                            passed_list.add((Boolean) details.get("passed"));
                            description_list.add((String) details.get("description"));
                            deadline_list.add((String) details.get("deadline"));
                            setby_list.add((String) details.get("setby"));

                            try {
                                Log.d("TAG", "Reached here" + details.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(new TaskListAdapter(title_list, passed_list, description_list, deadline_list, setby_list));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });


        return view;
    }
}