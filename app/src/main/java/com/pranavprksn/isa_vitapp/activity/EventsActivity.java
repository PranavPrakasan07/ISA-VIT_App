package com.pranavprksn.isa_vitapp.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.adapters.EventsAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pranavprksn.isa_vitapp.classes.OnSwipeTouchListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EventsActivity extends AppCompatActivity {

    ArrayList<String> poster_links = new ArrayList<>();
    ArrayList<String> title_list = new ArrayList<>();
    ArrayList<String> content_list = new ArrayList<>();
    ArrayList<Boolean> open_list = new ArrayList<>();
    ArrayList<String> reg_list = new ArrayList<>();
    ArrayList<String> youtube_list = new ArrayList<>();

    Map<String, Object> details = new Map<String, Object>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable @org.jetbrains.annotations.Nullable Object value) {
            return false;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object get(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object remove(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull @NotNull Map<? extends String, ?> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @NotNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Collection<Object> values() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ConstraintLayout events_activity_layout = findViewById(R.id.events_activity_layout);

        RecyclerView recyclerView = findViewById(R.id.events_recycler_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String event_type = "Events";

        try {
            event_type = getIntent().getStringExtra("eventType");
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.collection(event_type)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", document.getId() + " => " + document.getData());

                            details = (document.getData());

                            poster_links.add((String) details.get("poster"));
                            title_list.add((String) details.get("title"));
                            content_list.add((String) details.get("content"));
                            open_list.add((Boolean) details.get("open"));
                            reg_list.add((String) details.get("link"));
                            youtube_list.add((String) details.get("youtube"));

                            Log.d("TAG", "Links" + poster_links.toString());

                            Collections.reverse(poster_links);
                            Collections.reverse(title_list);
                            Collections.reverse(content_list);
                            Collections.reverse(open_list);
                            Collections.reverse(reg_list);
                            Collections.reverse(youtube_list);

                            try {
                                Log.d("TAG", "Reached here" + details.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(new EventsAdapter(poster_links, title_list, content_list, open_list, reg_list, youtube_list));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }
}