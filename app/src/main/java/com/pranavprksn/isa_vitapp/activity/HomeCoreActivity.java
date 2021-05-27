package com.pranavprksn.isa_vitapp.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.classes.MemberData;
import com.pranavprksn.isa_vitapp.fragment.ActivityCoreFragment;
import com.pranavprksn.isa_vitapp.fragment.Profile_Fragment;
import com.pranavprksn.isa_vitapp.fragment.TaskCoreFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class HomeCoreActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    public static String domain1, domain2;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?").setCancelable(true)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> HomeCoreActivity.super.onBackPressed()).create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_core);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        chipNavigationBar.setItemSelected(R.id.nav_activity, true);

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));
        Log.d("TAG : i", String.valueOf(R.id.nav_activity));
        Log.d("TAG : i", String.valueOf(R.id.nav_task));
        Log.d("TAG : i", String.valueOf(R.id.projects_header));

//        Toast.makeText(this, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String email = Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        DocumentReference docRefCore = db.collection("Core_Member_Data").document(email);

        Source source = Source.DEFAULT;

        docRefCore.get(source).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    MemberData data = document.toObject(MemberData.class);

                    assert data != null;

                    MemberData.member_name = data.getName();
                    MemberData.member_reg = data.getReg_number();
                    MemberData.member_domain1 = data.getDomain1();
                    MemberData.member_domain2 = data.getDomain2();

                    domain1 = data.getDomain1();
                    domain2 = data.getDomain2();

                    try {
                        Log.d("TAG Static", "Static : " + MemberData.member_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });

        chipNavigationBar.setOnItemSelectedListener(i -> {

            Fragment selectedFragment = null;

            if (chipNavigationBar.getSelectedItemId() == R.id.nav_activity) {
                Log.d("TAG : i", String.valueOf(i));

                selectedFragment = new ActivityCoreFragment();
            }

            if (chipNavigationBar.getSelectedItemId() == R.id.nav_task) {
                Log.d("TAG : i", String.valueOf(i));

                selectedFragment = new TaskCoreFragment();
            }

            if (chipNavigationBar.getSelectedItemId() == R.id.nav_profile) {
                Log.d("TAG : i", String.valueOf(i));

                selectedFragment = new Profile_Fragment();
            }

            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
        });

    }
}