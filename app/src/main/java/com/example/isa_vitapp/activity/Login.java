package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.HomeCoreActivity;
import com.example.isa_vitapp.MemberData;
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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;

    public static FirebaseAuth mAuth;
    public FetchFromDB[] dtls = new FetchFromDB[15];
    public FetchFromDB names = new FetchFromDB();

    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView login;
    TextView link, login_header, signup_link;
    static boolean isBoard = false;

    EditText email, password, registration_number;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            startActivity(new Intent(getApplicationContext(), Home.class));
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Log.d("Thread", "Executing thread name func" + FetchFromDB.position_name.keySet().toString());

        Log.d("User Status", String.valueOf(FirebaseAuth.getInstance().getCurrentUser()));

        login = findViewById(R.id.login_tab);

        link = findViewById(R.id.not_member);
        signup_link = findViewById(R.id.isavit_member);

        login_header = findViewById(R.id.heading);

        TextView message = findViewById(R.id.message);
        progressBar = findViewById(R.id.progressBar);

        message.setVisibility(View.INVISIBLE);

        email = findViewById(R.id.email_tab);
        password = findViewById(R.id.password_tab);
        registration_number = findViewById(R.id.regn_tab);

        mAuth = FirebaseAuth.getInstance();

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

        login_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        try {
            getBoardNames();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setIOSBundleId("com.example.ios")
                        .setAndroidPackageName(
                                "com.example.android",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                String registration_text = registration_number.getText().toString();

//                Toast.makeText(Login.this, registration_number.getText().toString(), Toast.LENGTH_SHORT).show();

//                startActivity(new Intent(getApplicationContext(), Home.class));

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

//                    message.setVisibility(View.VISIBLE);
//                    Animation fadeIn = new AlphaAnimation(0, 1);
//                    fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
//                    fadeIn.setDuration(1000);
//
////                Animation fadeOut = new AlphaAnimation(1, 0);
////                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
////                fadeOut.setStartOffset(1000);
////                fadeOut.setDuration(1000);
//
//                    AnimationSet animation = new AnimationSet(false); //change to false
//                    animation.addAnimation(fadeIn);
////                animation.addAnimation(fadeOut);
//                    message.setAnimation(animation);

//                    verify_user(email_text, password_text);
                    check_if_member(email_text, password_text, registration_text);
                }

            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.getId() == R.id.sign_in_button) {
//                    signIn();
//                }
//            }
//        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        login_header.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
                return false;
            }
        });

    }

    private void check_if_member(String email_text, String password_text, String registration_text){

        DocumentReference docRef = db.collection("Board_Member_Data").document(email_text);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        verify_user(email_text, password_text, registration_text, true);
                    } else {
//                        Toast.makeText(Login.this, "Not a member!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        DocumentReference docRefCore = db.collection("Core_Member_Data").document(email_text);
        docRefCore.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        verify_user(email_text, password_text, registration_text, false);
                    } else {
//                        Toast.makeText(Login.this, "Not a member!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void verify_user(String email_text, String password_text, String reg, boolean isBoard) {

        mAuth.signInWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    if(isBoard){
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }else{
                        startActivity(new Intent(getApplicationContext(), HomeCoreActivity.class));
                    }

                } else {

                    Toast.makeText(Login.this, "Failed login!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

//                    Intent intent = new Intent(getApplicationContext(), SignUp.class);
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("email", email_text);
//                    bundle.putString("password", password_text);
//                    bundle.putString("registration", reg);
//
//                    intent.putExtras(bundle);
//                    progressBar.setVisibility(View.GONE);
//
//                    startActivity(intent);
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

//                            Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    startActivity(new Intent(getApplicationContext(), LoginSplash.class));
                                }
                            }, 4000);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                            ;
                        }
                    }
                });
    }

    private void getBoardNames() throws InterruptedException {
//      New thread to perform background operation

        FetchFromDB.total_board_members = FetchFromDB.position_name.keySet().size();

//        Toast.makeText(this, String.valueOf(FetchFromDB.total_board_members), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(FetchFromDB.position_name.values()), Toast.LENGTH_SHORT).show();

        Thread getDetailThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("Thread", "Executing thread detail func" + Thread.currentThread().getName());
                Log.d("Thread", String.valueOf(FetchFromDB.position_name.values()));

                int i = 0;
                for (Object mem : FetchFromDB.position_name.values()) {
                    Log.d("Thread for loop", String.valueOf(mem));
                    //dtls[i].getBoardMemberDetails((String) mem);
                    i++;
                }
            }
        });

        getDetailThread.start();
        getDetailThread.join();
    }
}