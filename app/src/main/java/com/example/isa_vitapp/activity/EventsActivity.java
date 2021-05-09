package com.example.isa_vitapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isa_vitapp.EventsAdapter;
import com.example.isa_vitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class EventsActivity extends AppCompatActivity {

    ArrayList<String> poster_links = new ArrayList<>();
    private DatabaseReference mDatabase;
    Map<String, Object> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Events").document("Technitudes");

        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Toast.makeText(EventsActivity.this, document.getId(), Toast.LENGTH_SHORT).show();

                                details = document.getData();

                                Toast.makeText(EventsActivity.this, details.keySet().toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(EventsActivity.this, details.values().toString(), Toast.LENGTH_SHORT).show();

                                try {
                                    if(details.size() > 2) {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(new EventsAdapter(details));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }



                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}