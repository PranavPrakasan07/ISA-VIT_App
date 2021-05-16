package com.example.isa_vitapp.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.isa_vitapp.R;
import com.example.isa_vitapp.activity.Home;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.ShapeType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String BLUE = "#0C97E8";
    String GREY = "#747474";

    String task_title, task_desc, task_deadline;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTaskFragment newInstance(String param1, String param2) {
        CreateTaskFragment fragment = new CreateTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        NeumorphButton domain = view.findViewById(R.id.domain_name);

        ImageButton back_button = view.findViewById(R.id.back_button);
        NeumorphButton add_button = view.findViewById(R.id.add_button);

        EditText title = view.findViewById(R.id.title);
        EditText desc = view.findViewById(R.id.description);
        EditText deadline = view.findViewById(R.id.deadline);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        deadline.setText(formattedDate);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new AlertDialog.Builder(requireActivity())
//                        .setTitle("Logout?")
//                        .setMessage("Are you sure you want to go back?").setCancelable(true)
//                        .setNegativeButton(android.R.string.no, null)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DomainListFragment()).commit();
//                            }
//                        }).create().show();

            }
        });

        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                task_title = title.getText().toString();
                task_desc = desc.getText().toString();
                task_deadline = deadline.getText().toString();

                Toast.makeText(getActivity(), "Touched title", Toast.LENGTH_SHORT).show();
                if (task_title.equals("") || task_desc.equals("") || task_deadline.equals("")) {
                    add_button.setTextColor(Color.parseColor(GREY));
                } else {
                    add_button.setTextColor(Color.parseColor(BLUE));
                }
            }
        });

        desc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                task_title = title.getText().toString();
                task_desc = desc.getText().toString();
                task_deadline = deadline.getText().toString();

                Toast.makeText(getActivity(), "Touched desc", Toast.LENGTH_SHORT).show();
                if (task_title.equals("") || task_desc.equals("") || task_deadline.equals("")) {
                    add_button.setTextColor(Color.parseColor(GREY));
                } else {
                    add_button.setTextColor(Color.parseColor(BLUE));
                }
            }
        });

        deadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                task_title = title.getText().toString();
                task_desc = desc.getText().toString();
                task_deadline = deadline.getText().toString();

                Toast.makeText(getActivity(), "Touched desc", Toast.LENGTH_SHORT).show();
                if (task_title.equals("") || task_desc.equals("") || task_deadline.equals("")) {
                    add_button.setTextColor(Color.parseColor(GREY));
                } else {
                    add_button.setTextColor(Color.parseColor(BLUE));
                }
            }
        });


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_button.setShapeType(ShapeType.PRESSED);

                if (!(task_title.equals("") || task_desc.equals("") || task_deadline.equals(""))) {
                    add_button.setTextColor(Color.parseColor("#0C97E8"));
                } else {
                    add_button.setTextColor(Color.parseColor("#747474"));
                }

                Map<String, Object> docData = new HashMap<>();
                docData.put("title", task_title);
                docData.put("description", task_desc);
                docData.put("deadline", task_deadline);
                docData.put("passed", false);

                try {
                    docData.put("member", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection(DomainListFragment.domain_selected).document(formattedDate)
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                add_button.setShapeType(ShapeType.FLAT);
                                add_button.setText("Update");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });
            }
        });

        domain.setText(DomainListFragment.domain_selected);

        return view;
    }
}