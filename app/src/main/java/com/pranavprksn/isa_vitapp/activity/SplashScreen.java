package com.pranavprksn.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.pranavprksn.isa_vitapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

       new Handler().postDelayed(() -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if(user != null){
                Log.d("TAG", user.getEmail());

                DocumentReference docRefCore = db.collection("Core_Member_Data").document(Objects.requireNonNull(user.getEmail()));
                docRefCore.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            startActivity(new Intent(getApplicationContext(), HomeCoreActivity.class));
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                        startActivity(new Intent(getApplicationContext(), NoConnectionActivity.class));
                    }
                });

                DocumentReference docRef = db.collection("Board_Member_Data").document(Objects.requireNonNull(user.getEmail()));
                docRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                        startActivity(new Intent(getApplicationContext(), NoConnectionActivity.class));
                    }
                });
            }else{
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        }, 2000);
    }
}