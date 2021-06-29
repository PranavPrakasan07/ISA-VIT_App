package com.pranavprksn.isa_vitapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pranavprksn.isa_vitapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * For the core member to view the remarks given by the board
 */

public class MyTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public MyTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTaskFragment newInstance(String param1, String param2) {
        MyTaskFragment fragment = new MyTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_task, container, false);

        ImageButton back_button = view.findViewById(R.id.back_button);

        TextView domain_tab = view.findViewById(R.id.domain_name_tab);
        TextView title = view.findViewById(R.id.task_title);
        TextView deadline = view.findViewById(R.id.status);
        TextView remarks = view.findViewById(R.id.remarks_space);

        domain_tab.setOnClickListener(v -> {
            try {
                if(details.get("remarks") != null){
                    remarks.setText(String.valueOf(details.get("remarks")));
                }else{
                    remarks.setText("Not reviewed yet!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                remarks.setText("Not reviewed yet!");
            }
        });

        back_button.setOnClickListener(v -> requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, new MyTaskListFragment())
                .commit()
        );

        domain_tab.setText(ActivityCoreFragment.selected_domain);
        title.setText(MyTaskListFragment.task_clicked_title);
        deadline.setText(MyTaskListFragment.task_clicked_deadline);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Tasks")
                .document(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()))
                .collection(ActivityCoreFragment.selected_domain)
                .document(MyTaskListFragment.task_clicked_deadline)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Log.d("TAG", String.valueOf(documentSnapshot.getData()));

                    details = documentSnapshot.getData();

                    new Handler().postDelayed(() -> {
                        try {
                            if(details.get("remarks") != null){
                                remarks.setText(String.valueOf(details.get("remarks")));
                            }else{
                                remarks.setText("Not reviewed yet!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            remarks.setText("Not reviewed yet!");
                        }
                    }, 750);



                });

        return view;
    }
}