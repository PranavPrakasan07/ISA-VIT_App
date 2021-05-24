package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    public static FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView login, login_header, signup_link;

    EditText email, password, registration_number;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Log.d("User Status", String.valueOf(FirebaseAuth.getInstance().getCurrentUser()));

        login = findViewById(R.id.login_tab);
        signup_link = findViewById(R.id.isavit_member);
        login_header = findViewById(R.id.heading);
        progressBar = findViewById(R.id.progressBar);

        email = findViewById(R.id.email_tab);
        password = findViewById(R.id.password_tab);
        registration_number = findViewById(R.id.regn_tab);

        try {
            Bundle bundle = getIntent().getExtras();

            String email_string = bundle.getString("email");
            String password_string = bundle.getString("password");
            String reg_string = bundle.getString("registration");

            email.setText(email_string);
            password.setText(password_string);
            registration_number.setText(reg_string);

        } catch (Exception e) {
            e.printStackTrace();
        }

        login.setOnClickListener(view -> {
            String email_text = email.getText().toString();
            String password_text = password.getText().toString();
            String registration_text = registration_number.getText().toString();

            if (email_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your email", Toast.LENGTH_SHORT).show();
            } else if (password_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your password", Toast.LENGTH_SHORT).show();
            } else if (registration_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your registration number", Toast.LENGTH_SHORT).show();
            } else if (password_text.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
            } else if (!email_text.contains("@vitstudent.ac.in")) {
                Toast.makeText(Login.this, "Enter VIT Email address", Toast.LENGTH_SHORT).show();
            } else {

                progressBar.setVisibility(View.VISIBLE);

                check_if_member(email_text, password_text);
            }

        });

        signup_link.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUp.class)));

        login_header.setOnLongClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
            return false;
        });
    }

    private void check_if_member(String email_text, String password_text) {

        DocumentReference docRef = db.collection("Board_Member_Data").document(email_text);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    verify_user(email_text, password_text, true);
                } else {
                    Log.d("TAG", "Not a board member");
//                        Toast.makeText(Login.this, "Not a member!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                progressBar.setVisibility(View.GONE);

            }
        });

        DocumentReference docRefCore = db.collection("Core_Member_Data").document(email_text);
        docRefCore.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    verify_user(email_text, password_text,false);
                } else {
                    Log.d("TAG", "Not a core member");
//                        Toast.makeText(Login.this, "Not a member!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void verify_user(String email_text, String password_text, boolean isBoard) {

        mAuth.signInWithEmailAndPassword(email_text, password_text).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();

                if (isBoard) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeCoreActivity.class));
                }

            } else {
                Toast.makeText(Login.this, "Failed login!", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }

}