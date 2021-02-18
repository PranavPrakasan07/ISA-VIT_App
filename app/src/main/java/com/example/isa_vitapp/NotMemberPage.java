package com.example.isa_vitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NotMemberPage extends AppCompatActivity {

    Button github_isa, flagship_button, events_button, technitudes_button;
    ImageView member_image, photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_member_page);

        github_isa = findViewById(R.id.github_link);
        member_image = findViewById(R.id.member_photo);
        photos = findViewById(R.id.photos);

        flagship_button = findViewById(R.id.flagship_event_button);
        events_button = findViewById(R.id.events_button);
        technitudes_button = findViewById(R.id.technitudes_button);

        member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        github_isa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/isa-vit"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        flagship_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });

        events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });

        technitudes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });
    }
}