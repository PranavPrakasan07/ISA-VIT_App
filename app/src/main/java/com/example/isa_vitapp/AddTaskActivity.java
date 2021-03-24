package com.example.isa_vitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddTaskActivity extends AppCompatActivity {

    Button create_in_splash, tutorial_button;
    LinearLayout splash, task_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//
//        СircularPickerView(context).apply {
//            colors = (intArrayOf(
//                    Color.parseColor("#00EDE9"),
//                    Color.parseColor("#0087D9"),
//                    Color.parseColor("#8A1CC3")))
//            gradientAngle = 220
//            maxLapCount = 2
//            currentValue = 13
//            maxValue = 24
//            centeredTextSize = 60f
//            centeredText = "Hours"

        splash = findViewById(R.id.create_task_splash);
        task_layout = findViewById(R.id.create_task_layout);

        create_in_splash = findViewById(R.id.create_task_button_splash);

        Animation animation_fadein, animation_fadeout;

        animation_fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation_fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        create_in_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash.startAnimation(animation_fadeout);
                task_layout.startAnimation(animation_fadein);
                task_layout.setVisibility(View.VISIBLE);
            }
        });




    }
}