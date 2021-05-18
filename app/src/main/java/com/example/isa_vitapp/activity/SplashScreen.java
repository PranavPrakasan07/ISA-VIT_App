package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.R;

public class SplashScreen extends AppCompatActivity {

    LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        background = findViewById(R.id.background_layout);

        background.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
            finish();
        });
    }
}