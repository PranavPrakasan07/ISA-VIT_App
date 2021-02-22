package com.example.isa_vitapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    protected static FirebaseAuth mAuth;

    Button login, click;
    TextView link, login_header;
    static boolean isBoard = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText username, password, registration_number;

    int count = 0;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login_button);

        link = findViewById(R.id.not_member_link);
        login_header = findViewById(R.id.login_header);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        registration_number = findViewById(R.id.registration_number_field);

//        GradientDrawable custom_button = (GradientDrawable) login.getBackground();
//        custom_button.setStroke(convertDpToPx(3), Color.parseColor("#8AFF96"));

        //Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

//        final GradientDrawable[] myGrad = new GradientDrawable[1];

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotMemberPage.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        login_header.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
                return false;
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                flag = 0;
//
//                if (username.getText().toString().equals("")) {
//                    username.setBackgroundResource(R.drawable.button_back_red);
//                    custom_button.setStroke(convertDpToPx(3), Color.parseColor("#FF796F"));
//                    username.startAnimation(animShake);
//                    flag = 1;
//                } else {
//                    username.setBackgroundResource(R.drawable.button_back_white);
//                }
//                if (registration_number.getText().toString().equals("")) {
//                    registration_number.setBackgroundResource(R.drawable.button_back_red);
//                    custom_button.setStroke(convertDpToPx(3), Color.parseColor("#FF796F"));
//                    registration_number.startAnimation(animShake);
//                    flag = 1;
//                } else {
//                    registration_number.setBackgroundResource(R.drawable.button_back_white);
//                }
//                if (password.getText().toString().equals("")) {
//                    password.setBackgroundResource(R.drawable.button_back_red);
//                    custom_button.setStroke(convertDpToPx(3), Color.parseColor("#FF796F"));
//                    password.startAnimation(animShake);
//                    flag = 1;
//                } else {
//                    password.setBackgroundResource(R.drawable.button_back_white);
//                }
//
//                if (flag == 0) {
//                    username.setBackgroundResource(R.drawable.button_back_white);
//                    registration_number.setBackgroundResource(R.drawable.button_back_white);
//                    password.setBackgroundResource(R.drawable.button_back_white);
//                    custom_button.setStroke(convertDpToPx(3), Color.parseColor("#8AFF96"));
//
//
//                    Runnable checkBoard = new Runnable() {
//                        @Override
//                        public void run() {
//
//                            DocumentReference noteRef = db.collection("BoardMemberDetails").document(registration_number.getText().toString());
//
//                            Log.d("TAG", "Began verification");
//
//                            noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    if (documentSnapshot.exists()) {
//                                        Log.d("TAG", "Board Found");
//                                        if (username.getText().toString().equals(documentSnapshot.get("PEmail")) || username.getText().toString().equals(documentSnapshot.get("VEmail")))
//                                            isBoard = true;
//                                        else
//                                            isBoard = false;
//                                    } else {
//                                        Log.d("TAG", "Board not found");
//                                        isBoard = false;
//                                    }
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("TAG", e.toString());
//                                        }
//                                    });
//                        }
//                    };
//
//                    Thread checkForBoard = new Thread(checkBoard);
//                    checkForBoard.start();
//                    try {
//                        checkForBoard.join();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    DocumentReference noteRef = db.collection("CoreMemberDetails").document(registration_number.getText().toString());
//
//                    Log.d("TAG", "Began verification");
//
//                    noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            if (documentSnapshot.exists()) {
//                                Log.d("TAG", "Board Found");
//                                if (username.getText().toString().equals(documentSnapshot.get("PEmail")) || username.getText().toString().equals(documentSnapshot.get("VEmail")))
//                                    login_user(username.getText().toString(), password.getText().toString());
//
//                            } else {
//                                Log.d("TAG", "Board not found");
//                            }
//                        }
//                    })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("TAG", e.toString());
//                                }
//                            });
//                }
//
//            }
//        });
//
//
//    }
//
//    private void login_user(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            startActivity(new Intent(getApplicationContext(), MainMenuBoard.class));
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("TAG", "signInWithEmail:failure", task.getException());
//                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//
//    private int convertDpToPx(int dp) {
//        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}