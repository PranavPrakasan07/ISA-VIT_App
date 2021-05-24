package com.example.isa_vitapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.isa_vitapp.classes.MemberData;
import com.example.isa_vitapp.R;
import com.example.isa_vitapp.fragment.Add_Fragment;
import com.example.isa_vitapp.fragment.Profile_Fragment;
import com.example.isa_vitapp.fragment.Search_Fragment;
import com.example.isa_vitapp.fragment.Task_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    public static ArrayList<String> history_list = new ArrayList<>(5);
    public static ArrayList<String> vit_email_list = new ArrayList<>(5);

    public static int counter = 0;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout?")
                .setMessage("Are you sure you want to logout?").setCancelable(true)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Home.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        chipNavigationBar.setItemSelected(R.id.nav_new_add, true);

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));
        Log.d("TAG : i", String.valueOf(R.id.nav_new_add));
        Log.d("TAG : i", String.valueOf(R.id.nav_search));
        Log.d("TAG : i", String.valueOf(R.id.nav_task));
        Log.d("TAG : i", String.valueOf(R.id.projects_header));

//        Toast.makeText(this, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
        Log.d("TAG", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String email = Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

        DocumentReference docRefCore = db.collection("Board_Member_Data").document(email);
        docRefCore.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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

                        try {
                            Log.d("TAG Static", "Static : " + MemberData.member_name);
//                            Toast.makeText(Home.this, "Static : " + MemberData.member_name, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
//                        Toast.makeText(Login.this, "Not a member!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {

                Fragment selectedFragment = null;

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_new_add) {
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Add_Fragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_search) {
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Search_Fragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_task) {
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Task_Fragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_profile) {
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Profile_Fragment();
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            }
        });
    }
}