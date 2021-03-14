package com.example.isa_vitapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.R;
import com.example.isa_vitapp.fragment.Add_Fragment;
import com.example.isa_vitapp.fragment.Profile_Fragment;
import com.example.isa_vitapp.fragment.Search_Fragment;
import com.example.isa_vitapp.fragment.Task_Fragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        chipNavigationBar.setItemSelected(R.id.nav_new_add, true);

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));
        Log.d("TAG : i", String.valueOf(R.id.nav_new_add));
        Log.d("TAG : i", String.valueOf(R.id.nav_search));
        Log.d("TAG : i", String.valueOf(R.id.nav_task));
        Log.d("TAG : i", String.valueOf(R.id.projects_header));

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {

                Fragment selectedFragment = null;

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_new_add){
                    Toast.makeText(getApplicationContext(), "Add Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Add_Fragment();
                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_search){
                    Toast.makeText(getApplicationContext(), "Search Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Search_Fragment();
                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_task){
                    Toast.makeText(getApplicationContext(), "Task Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Task_Fragment();
                }

                if(chipNavigationBar.getSelectedItemId() == R.id.nav_profile){
                    Toast.makeText(getApplicationContext(), "Profile Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new Profile_Fragment();
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            }
        });
    }
}