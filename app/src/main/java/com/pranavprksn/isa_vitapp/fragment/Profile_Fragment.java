package com.pranavprksn.isa_vitapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.activity.AboutActivity;
import com.pranavprksn.isa_vitapp.activity.Home;
import com.pranavprksn.isa_vitapp.activity.LogoutSplash;
import com.pranavprksn.isa_vitapp.classes.MemberData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pranavprksn.isa_vitapp.classes.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import soup.neumorphism.NeumorphCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton home, logout;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Fragment newInstance(String param1, String param2) {
        Profile_Fragment fragment = new Profile_Fragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        ConstraintLayout fragment_profile_layout = view.findViewById(R.id.constraint_layout);

        fragment_profile_layout.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                Fragment domainList = new Task_Fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, domainList).commit();
                Home.chipNavigationBar.setItemSelected(R.id.nav_task, true);
            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {
            }
        });

        logout = view.findViewById(R.id.logout);
        home = view.findViewById(R.id.home_button);

        NeumorphCardView position = view.findViewById(R.id.n_position_card);

        TextView name = view.findViewById(R.id.name_tab);
        TextView board_position = view.findViewById(R.id.board_position_name);
        TextView vit_email = view.findViewById(R.id.vit_email);
        TextView personal_email = view.findViewById(R.id.p_email);
        TextView reg_number = view.findViewById(R.id.reg_n);
        TextView room_number = view.findViewById(R.id.room_n);
        TextView mobile = view.findViewById(R.id.mobile_n);
        TextView dob = view.findViewById(R.id.dob);

        TextView domain1 = view.findViewById(R.id.domain1);
        TextView domain2 = view.findViewById(R.id.domain2);

        ImageView photo = view.findViewById(R.id.photo);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String email = Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        DocumentReference docRef = db.collection("Board_Member_Data").document(email);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    MemberData data = document.toObject(MemberData.class);

                    assert data != null;

                    name.setText(data.getName());
                    board_position.setText(data.getPosition());
                    vit_email.setText(data.getVit_email());
                    personal_email.setText(data.getPersonal_email());
                    reg_number.setText(data.getReg_number());
                    room_number.setText(data.getRoom_number());
                    mobile.setText(data.getContact_number());
                    dob.setText(data.getDob());

                    if(data.getDomain1().equals("Events and Finance")){
                        domain1.setText("E&F");
                    }else{
                        domain1.setText(data.getDomain1());
                    }

                    if(data.getDomain2().equals("Content and Outreach")){
                        domain2.setText("C&O");
                    }else{
                        domain2.setText(data.getDomain2());
                    }

                    try {
                        Picasso.get()
                                .load(data.getPhoto_link())
                                .into(photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("TAG", "failed");
//                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());

            }
        });

        DocumentReference docRefCore = db.collection("Core_Member_Data").document(email);
        docRefCore.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    MemberData data = document.toObject(MemberData.class);

                    assert data != null;

                    position.setVisibility(View.GONE);

                    name.setText(data.getName());
                    board_position.setText(data.getPosition());
                    vit_email.setText(data.getVit_email());
                    personal_email.setText(data.getPersonal_email());
                    reg_number.setText(data.getReg_number());
                    room_number.setText(data.getRoom_number());
                    mobile.setText(data.getContact_number());
                    dob.setText(data.getDob());

                    if(data.getDomain1().equals("Events and Finance")){
                        domain1.setText("E&F");
                    }else{
                        domain1.setText(data.getDomain1());
                    }

                    if(data.getDomain2().equals("Content and Outreach")){
                        domain2.setText("C&O");
                    }else{
                        domain2.setText(data.getDomain2());
                    }

                    try {
                        Picasso.get()
                                .load(data.getPhoto_link())
                                .into(photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("TAG", "failed");
//                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });

        logout.setOnClickListener(v -> {
            Log.d("GG", "Logout Clicked");

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
            }

            startActivity(new Intent(getContext(), LogoutSplash.class));
            requireActivity().finish();

        });

        home.setOnClickListener(v -> {
            Log.d("GG", "Home Clicked");
            startActivity(new Intent(getContext(), AboutActivity.class));
        });

        return view;
    }
}