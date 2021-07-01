package com.pranavprksn.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pranavprksn.isa_vitapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pranavprksn.isa_vitapp.classes.OnSwipeTouchListener;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView signup, link;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText email, password, retype_password, registration_number;
    String email_text, password_text, retype_password_text, registration_text;

    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        ConstraintLayout signup_layout = findViewById(R.id.signup_layout);

        signup = findViewById(R.id.signup_tab);
        link = findViewById(R.id.login_link);
        email = findViewById(R.id.email_tab);
        password = findViewById(R.id.password_tab);
        retype_password = findViewById(R.id.retype_tab);
        registration_number = findViewById(R.id.regn_tab);
        progressBar = findViewById(R.id.progressBar);

        TextView not_member = findViewById(R.id.not_member);

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

        signup_layout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
            }
        });

        signup.setOnClickListener(v -> {
            email_text = email.getText().toString();
            password_text = password.getText().toString();
            retype_password_text = retype_password.getText().toString();
            registration_text = registration_number.getText().toString();

            if (email_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your email", Toast.LENGTH_SHORT).show();
            } else if (password_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your password", Toast.LENGTH_SHORT).show();
            } else if (registration_text.equals("")) {
                Toast.makeText(getApplicationContext(), "Fill in your registration number", Toast.LENGTH_SHORT).show();
            } else if (password_text.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
            } else if (!password_text.equals(retype_password_text)) {
                Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            } else if (!email_text.contains("@vitstudent.ac.in")) {
                Toast.makeText(SignUp.this, "Enter VIT Email address", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                check_if_member(email_text, password_text, registration_text);
            }
        });

//        link.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), Login.class);
//            Bundle bundle = new Bundle();
//
//            bundle.putString("email", email_text);
//            bundle.putString("password", password_text);
//            bundle.putString("registration", registration_text);
//
//            intent.putExtras(bundle);
//
//            startActivity(intent);
//        });

        not_member.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        });
    }

    private void check_if_member(String email_text, String password_text, String registration_text) {

        final boolean[] found = {false};

        DocumentReference docRef = db.collection("Board_Member_Data").document(email_text);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    found[0] = true;
                    createAccount(email_text, password_text, registration_text, true);
                } else {

                    if (!found[0]) {
                        Log.d("TAG", "Not a board member");
//                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    }
                    progressBar.setVisibility(View.GONE);

                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                startActivity(new Intent(getApplicationContext(), NoConnectionActivity.class));
                progressBar.setVisibility(View.GONE);
            }
        });

        DocumentReference docRefCore = db.collection("Core_Member_Data").document(email_text);
        docRefCore.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    found[0] = true;
                    createAccount(email_text, password_text, registration_text, false);
                } else {

                    if (!found[0]) {
                        Log.d("TAG", "Not a member");
//                        Toast.makeText(SignUp.this, "Not a core member!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    }
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), NoConnectionActivity.class));

            }
        });
    }

    private void createAccount(String email_text, String password_text, String registration_text, boolean isBoard) {

        mAuth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                progressBar.setVisibility(View.GONE);

                if (isBoard) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeCoreActivity.class));
                }
                finish();

                // send verification link
//
//                    FirebaseUser fuser = mAuth.getCurrentUser();
//                    assert fuser != null;
//                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(SignUp.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//
//                            if(isBoard){
//                                startActivity(new Intent(getApplicationContext(), Home.class));
//                            }else{
//                                startActivity(new Intent(getApplicationContext(), HomeCoreActivity.class));
//                            }
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    });


            } else {
                Toast.makeText(SignUp.this, "Seems like you already have an account!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                Bundle bundle = new Bundle();

                bundle.putString("email", email_text);
                bundle.putString("password", password_text);
                bundle.putString("registration", registration_text);

                intent.putExtras(bundle);

                startActivity(intent);
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }

}