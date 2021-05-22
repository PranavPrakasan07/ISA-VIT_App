package com.example.isa_vitapp.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.activity.HomeCoreActivity;
import com.example.isa_vitapp.classes.ConstantsClass;
import com.example.isa_vitapp.classes.MemberData;
import com.example.isa_vitapp.classes.TaskData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import soup.neumorphism.NeumorphButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskCoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskCoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView title_domain1;
    TextView title_domain2;
    TextView desc_domain1;
    TextView desc_domain2;
    TextView deadline_domain1;
    TextView deadline_domain2;

    ImageButton done1;
    ImageButton done2;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskCoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskCoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskCoreFragment newInstance(String param1, String param2) {
        TaskCoreFragment fragment = new TaskCoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_task_core, container, false);

        NeumorphButton domain1 = view.findViewById(R.id.domain1_header);
        NeumorphButton domain2 = view.findViewById(R.id.domain2_header);

        title_domain1 = view.findViewById(R.id.title_domain1);
        title_domain2 = view.findViewById(R.id.title_domain2);
        desc_domain1 = view.findViewById(R.id.desc_domain1);
        desc_domain2 = view.findViewById(R.id.desc_domain2);
        deadline_domain1 = view.findViewById(R.id.deadline_domain1);
        deadline_domain2 = view.findViewById(R.id.deadline_domain2);

        done1 = view.findViewById(R.id.done_domain1);
        done2 = view.findViewById(R.id.done_domain2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("TASK", HomeCoreActivity.domain1);
        Source source = Source.DEFAULT;

        db.collection("Task_" + HomeCoreActivity.domain1)
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            TaskData taskData1 = null;

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                taskData1 = document.toObject(TaskData.class);

                                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                                Date d1 = new Date();
                                Date d2 = null;

                                try {
                                    d2 = new SimpleDateFormat("yyyy-MM-dd").parse(taskData1.getDeadline());

                                    Toast.makeText(getActivity(), String.valueOf(d1) + d2, Toast.LENGTH_SHORT).show();

                                    if (d1.compareTo(d2) > 0) {
                                        deadline_domain1.setTextColor(Color.parseColor(ConstantsClass.BLUE_LIGHT));
                                        setTaskDetails(taskData1, 1);
                                        Toast.makeText(getActivity(), "Old", Toast.LENGTH_SHORT).show();
                                    } else if (d1.compareTo(d2) <= 0) {
                                        deadline_domain1.setTextColor(Color.parseColor(ConstantsClass.BLUE_LIGHT));
                                        setTaskDetails(taskData1, 1);
                                        Toast.makeText(getActivity(), "New", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        Log.d("TASK", HomeCoreActivity.domain2);
        db.collection("Task_" + HomeCoreActivity.domain2)
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            TaskData taskData2 = null;

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());


                                taskData2 = document.toObject(TaskData.class);

                                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                                Date d1 = new Date();
                                Date d2 = null;

                                try {
                                    d2 = new SimpleDateFormat("yyyy-MM-dd").parse(taskData2.getDeadline());

                                    Toast.makeText(getActivity(), String.valueOf(d1) + d2, Toast.LENGTH_SHORT).show();

                                    if (d1.compareTo(d2) > 0) {
                                        deadline_domain2.setTextColor(Color.parseColor(ConstantsClass.FIELD_COLOR));
                                        setTaskDetails(taskData2, 2);
                                        Toast.makeText(getActivity(), "Old", Toast.LENGTH_SHORT).show();
                                    } else if (d1.compareTo(d2) <= 0) {
                                        deadline_domain2.setTextColor(Color.parseColor(ConstantsClass.BLUE_LIGHT));
                                        setTaskDetails(taskData2, 2);
                                        Toast.makeText(getActivity(), "New", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        domain1.setText(HomeCoreActivity.domain1);
        domain2.setText(HomeCoreActivity.domain2);

        return view;
    }

    public void setTaskDetails(TaskData taskData, int domain) {

        if (domain == 1) {

            try {
                Log.d("TAGSET", TaskData.task_title1);
                desc_domain1.setText(taskData.getDescription());
                deadline_domain1.setText(taskData.getDeadline());
                title_domain1.setText(taskData.getTitle());

                if (taskData.getPassed()) {
                    done1.setImageResource(R.drawable.ic_grey_round);
                } else {
                    done1.setImageResource(R.drawable.ic_blue_round);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                Log.d("TAGSET", TaskData.task_title2);
                desc_domain2.setText(taskData.getDescription());
                deadline_domain2.setText(taskData.getDeadline());
                title_domain2.setText(taskData.getTitle());

                if (taskData.getPassed()) {
                    done2.setImageResource(R.drawable.ic_grey_round);
                } else {
                    done2.setImageResource(R.drawable.ic_blue_round);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}