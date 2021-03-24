package com.example.isa_vitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class RemoveTaskActivity extends AppCompatActivity {

    LinearLayout splash;
    Button remove_in_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_task);

        splash = findViewById(R.id.remove_task_splash);
        remove_in_splash = findViewById(R.id.remove_task_button_splash);

        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        remove_in_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash.startAnimation(animation);
                remove_in_splash.setVisibility(View.VISIBLE);
            }
        });
    }
}