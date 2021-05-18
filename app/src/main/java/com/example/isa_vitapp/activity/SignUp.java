package com.example.isa_vitapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isa_vitapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView signup, click;
    TextView link;
    static boolean isBoard = false;

    private int RC_SIGN_IN = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText email, password, retype_password, registration_number;
    String email_text, password_text, retype_password_text, registration_text;

    ImageButton signInButton;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;

    int count = 0;
    int flag = 0;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.signup_tab);

        link = findViewById(R.id.login_link);

        email = findViewById(R.id.email_tab);
        password = findViewById(R.id.password_tab);
        retype_password = findViewById(R.id.retype_tab);
        registration_number = findViewById(R.id.regn_tab);

        progressBar = findViewById(R.id.progressBar);

        TextView not_member = findViewById(R.id.not_member);

//        signInButton = findViewById(R.id.signInButton);

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                String retype_password_text = retype_password.getText().toString();
                String registration_text = registration_number.getText().toString();

//                Toast.makeText(Login.this, registration_number.getText().toString(), Toast.LENGTH_SHORT).show();

//                startActivity(new Intent(getApplicationContext(), Home.class));

                if (email_text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in your email", Toast.LENGTH_SHORT).show();
                } else if (password_text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in your password", Toast.LENGTH_SHORT).show();
                }else if (registration_text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in your registration number", Toast.LENGTH_SHORT).show();
                }
                else if (password_text.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
                }else if(!password_text.equals(retype_password_text)) {
                    Toast.makeText(SignUp.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }else if(!email_text.contains("@vitstudent.ac.in")) {
                    Toast.makeText(SignUp.this, "Enter VIT Email address", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    createAccount(email_text, password_text, registration_text);
                }
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                Bundle bundle = new Bundle();

                bundle.putString("email", email_text);
                bundle.putString("password", password_text);
                bundle.putString("registration", registration_text);

                intent.putExtras(bundle);

                startActivity(intent);            }
        });

        not_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);            }
        });


    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            assert acc != null;
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String email = account.getEmail();

            Uri photo = account.getPhotoUrl();

            startActivity(new Intent(getApplicationContext(), Home.class));

            Toast.makeText(this, personName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, personGivenName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, personFamilyName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        }
    }

    private void createAccount(String email_text, String password_text, String registration_text) {

        mAuth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // send verification link

                    FirebaseUser fuser = mAuth.getCurrentUser();
                    assert fuser != null;
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SignUp.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), LoginSplash.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Toast.makeText(SignUp.this, "Failed!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("email", email_text);
                    bundle.putString("password", password_text);
                    bundle.putString("registration", registration_text);

                    intent.putExtras(bundle);

                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }
}