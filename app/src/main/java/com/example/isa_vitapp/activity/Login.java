package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.LoginSplash;
import com.example.isa_vitapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;

    public static FirebaseAuth mAuth;
    public FetchFromDB[] dtls = new FetchFromDB[15];
    public FetchFromDB names = new FetchFromDB();

    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button login, click;
    TextView link, login_header;
    static boolean isBoard = false;

    private Handler mHandler;
    private ProgressBar mProgressBar;

    EditText username, password, registration_number;

    int count = 0;
    int flag = 0;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
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

        login = findViewById(R.id.login_button);

        link = findViewById(R.id.not_member_link);
        login_header = findViewById(R.id.login_header);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        registration_number = findViewById(R.id.registration_number_field);

        mProgressBar = findViewById(R.id.progressBar_splash);
        mAuth = FirebaseAuth.getInstance();

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
                String email_text = username.getText().toString();
                String password_text = password.getText().toString();

                if (email_text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in your email", Toast.LENGTH_SHORT).show();
                } else if (password_text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill in your password", Toast.LENGTH_SHORT).show();
                } else if (password_text.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
                } else {
                    verify_user(email_text, password_text);
                    createAccount(email_text, password_text);
                }

            }
        });
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_button) {
                    signIn();
                }
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotMemberPage.class));
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


    private void createAccount(String email_text, String password_text) {


        Login.mAuth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // send verification link

                    FirebaseUser fuser = Login.mAuth.getCurrentUser();
                    assert fuser != null;
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginSplash.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void verify_user(String email_text, String password_text) {

        mAuth.signInWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), LoginSplash.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getApplicationContext(), LoginSplash.class));
                                }
                            }, 4000);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                            ;
                        }
                    }
                });
    }

    private void getBoardNames() throws InterruptedException {
//      New thread to perform background operation

        FetchFromDB.total_board_members = FetchFromDB.position_name.keySet().size();

        Toast.makeText(this, String.valueOf(FetchFromDB.total_board_members), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(FetchFromDB.position_name.values()), Toast.LENGTH_SHORT).show();

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