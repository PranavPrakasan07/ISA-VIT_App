package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.isa_vitapp.R;

public class LoginSplash extends AppCompatActivity {

    NumberProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        progress_bar = findViewById(R.id.number_progress_bar);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                int i = 0;
                while (i < 10000) {
                    progress_bar.setProgress(i / 100);
                    i++;
                }
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        }, 1500);   //1.5 seconds
    }
}