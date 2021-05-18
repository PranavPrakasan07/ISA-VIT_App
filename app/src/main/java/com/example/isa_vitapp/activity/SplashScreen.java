package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.R;

public class SplashScreen extends AppCompatActivity {

    LinearLayout background;

    FetchFromDB names = new FetchFromDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        background = findViewById(R.id.background_layout);

        try {
            getBoardNames();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        background.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
            finish();
        });
    }

    private void getBoardNames() throws InterruptedException {
//      New thread to perform background operation

        Thread getNameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                names.getBoardMemberNames();
                Log.d("Thread", "Executing thread name func" + Thread.currentThread().getName());
            }
        });

        getNameThread.start();
    }
}