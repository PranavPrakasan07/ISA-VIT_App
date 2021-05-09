package com.example.isa_vitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.isa_vitapp.R;

public class AboutActivity extends AppCompatActivity {

    TextView flag_ship, technitude, prev_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        flag_ship = findViewById(R.id.flagship_button);
        technitude = findViewById(R.id.technitude_button);
        prev_event = findViewById(R.id.prev_button);

        flag_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
            }
        });

        technitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
            }
        });

        prev_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
            }
        });
    }
}