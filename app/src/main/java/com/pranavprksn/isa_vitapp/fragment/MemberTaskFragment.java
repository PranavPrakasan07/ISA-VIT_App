package com.pranavprksn.isa_vitapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.classes.ConstantsClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * For board members to comment on the tasks submitted, blue button to submit remarks
 */

public class MemberTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String remarks_text = "";

    public MemberTaskFragment() {
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
    public static MemberTaskFragment newInstance(String param1, String param2) {
        MemberTaskFragment fragment = new MemberTaskFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_task, container, false);

        ImageButton back_button = view.findViewById(R.id.back_button);

        back_button.setOnClickListener(v ->
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new MemberTaskListFragment())
                        .commit()
        );

        TextView domain_name = view.findViewById(R.id.domain_name_tab);
        TextView member_name = view.findViewById(R.id.member_name_tab);
        TextView task_title = view.findViewById(R.id.task_title);
        TextView task_deadline = view.findViewById(R.id.status);
        EditText remarks = view.findViewById(R.id.remarks_space);

        ImageButton done_button = view.findViewById(R.id.done_button);

        String domain_name_text = Task_Fragment.domain_selected;
        String member_name_text = MemberListFragment.selected_core_member_name;
        String member_email_text = MemberListFragment.selected_core_member_email;
        String task_deadline_text = MemberTaskListFragment.task_clicked_deadline;
        String task_title_text = MemberTaskListFragment.task_clicked_title;

        domain_name.setText(domain_name_text);
        member_name.setText(member_name_text);

        task_title.setText(task_title_text);
        task_deadline.setText(task_deadline_text);

        Date d1 = new Date();
        Date d2 = null;

        try {
            d2 = new SimpleDateFormat("yyyy-MM-dd").parse(task_deadline_text);

            if (d1.after(d2)) {
                task_deadline.setTextColor(Color.parseColor(ConstantsClass.FIELD_COLOR));

            } else if (d1.before(d2)) {
                task_deadline.setTextColor(Color.parseColor(ConstantsClass.BLUE_LIGHT));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        done_button.setOnClickListener(v -> {

            Map<String, String> task_details = new HashMap<>();

            remarks_text = remarks.getText().toString();

            task_details.put("title", task_title_text);
            task_details.put("remarks", remarks_text);

            db.collection("Tasks")
                    .document(member_email_text)
                    .collection(domain_name_text)
                    .document(task_deadline_text)
                    .set(task_details, SetOptions.merge())
                    .addOnSuccessListener(unused -> {
                        Log.d("TAG", "Successful to write document");
                        Toast.makeText(getActivity(), "Remarks submitted!", Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener(e -> {
                        Log.d("TAG", "Failed to write document");
                        Toast.makeText(getActivity(), "Failed to submit remarks!", Toast.LENGTH_SHORT).show();

                    });
        });

        return view;
    }
}