package com.example.isa_vitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button login, click;
    TextView link;
    static boolean isBoard = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText username, password, registration_number;

    int count = 0;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login_button);

        link = findViewById(R.id.not_member_link);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        registration_number = findViewById(R.id.registration_number_field);



    }
}