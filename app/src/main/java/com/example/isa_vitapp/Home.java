package com.example.isa_vitapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));
        Log.d("TAG : i", String.valueOf(R.id.nav_new_add));
        Log.d("TAG : i", String.valueOf(R.id.nav_search));
        Log.d("TAG : i", String.valueOf(R.id.nav_task));
        Log.d("TAG : i", String.valueOf(R.id.projects_header));

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_new_add){
                    Toast.makeText(getApplicationContext(), "Add Selected", Toast.LENGTH_SHORT).show();

                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_task){
                    Toast.makeText(getApplicationContext(), "Task Selected", Toast.LENGTH_SHORT).show();
                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_profile){
                    Toast.makeText(getApplicationContext(), "Profile Selected", Toast.LENGTH_SHORT).show();
                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_search){
                    Toast.makeText(getApplicationContext(), "Search Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}