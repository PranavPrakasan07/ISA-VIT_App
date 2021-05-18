package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.R;

public class LogoutSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }, 2800);   //2.8 seconds
    }
}