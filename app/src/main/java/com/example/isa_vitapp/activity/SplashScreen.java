package com.example.isa_vitapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.R;
import com.example.isa_vitapp.activity.Login;

public class SplashScreen extends AppCompatActivity {

    ConstraintLayout background;

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

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
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