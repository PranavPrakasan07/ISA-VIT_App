package com.pranavprksn.isa_vitapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavprksn.isa_vitapp.R;
import com.pranavprksn.isa_vitapp.adapters.BoardListAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.ShapeType;

public class AboutActivity extends AppCompatActivity {

    NeumorphCardView flag_ship, technitude;
    ImageView isa_instagram, isa_linkedin, isa_github, isa_medium, isa_youtube;
    Button github_isa;

    ArrayList<String> name_list = new ArrayList<>();
    ArrayList<String> position_list = new ArrayList<>();
    ArrayList<String> instagram_list = new ArrayList<>();
    ArrayList<String> linkedin_list = new ArrayList<>();
    ArrayList<String> photo_list = new ArrayList<>();

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
        setContentView(R.layout.activity_about);

        flag_ship = findViewById(R.id.n_flagship_card);
        technitude = findViewById(R.id.n_technitude_card);

        isa_linkedin = findViewById(R.id.isa_linkedin_icon);
        isa_github = findViewById(R.id.isa_github_icon);
        isa_instagram = findViewById(R.id.isa_instagram_icon);
        isa_medium = findViewById(R.id.isa_medium_icon);
        isa_youtube = findViewById(R.id.isa_youtube_icon);
        github_isa = findViewById(R.id.github_link);

        LinearLayout hiddenView = findViewById(R.id.hidden_view);
        NeumorphCardView cardView = findViewById(R.id.base_cardview);
        RelativeLayout fixed_layout = findViewById(R.id.fixed_layout);

        NeumorphCardView created_by_card = findViewById(R.id.fixed_card);

        ImageButton back_button = findViewById(R.id.back_button);
        ImageView developer = findViewById(R.id.photo_devl);
        ImageView ldesigner = findViewById(R.id.photo_ld);
        ImageView designer2 = findViewById(R.id.photo_d2);
        ImageView designer3 = findViewById(R.id.photo_d3);
        ImageView designer4 = findViewById(R.id.photo_d4);

        back_button.setOnClickListener(v -> onBackPressed());

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/quizapp-ddf42.appspot.com/o/pranav.png?alt=media&token=0aecdc2a-334a-42b8-a94a-717c31184a4b")
                .into(developer);

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/quizapp-ddf42.appspot.com/o/pratham.png?alt=media&token=fe9651c8-36ff-4abc-a78c-34bbe5d1024b")
                .into(ldesigner);

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/quizapp-ddf42.appspot.com/o/vatsal.png?alt=media&token=d58aa24f-8692-4ed1-9446-e72b5977cbd4")
                .into(designer2);

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/quizapp-ddf42.appspot.com/o/prishita.png?alt=media&token=862d8db1-ec28-46b0-8c18-615a388e5cf4")
                .into(designer3);

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/quizapp-ddf42.appspot.com/o/anannya.png?alt=media&token=d4c6637e-c82c-4619-8317-059b6d32ad00")
                .into(designer4);


        fixed_layout.setOnClickListener(view -> {

            created_by_card.setShapeType(ShapeType.PRESSED);

            // If the CardView is already expanded, set its visibility
            //  to gone and change the expand less icon to expand more.
            if (hiddenView.getVisibility() == View.VISIBLE) {
                created_by_card.setShapeType(ShapeType.FLAT);

                // The transition of the hiddenView is carried out
                //  by the TransitionManager class.
                // Here we use an object of the AutoTransition
                // Class to create a default transition.
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());

                hiddenView.setVisibility(View.GONE);

            }

            // If the CardView is not expanded, set its visibility
            // to visible and change the expand more icon to expand less.
            else {
                TransitionManager.beginDelayedTransition(cardView,
                        new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(1000);

                AnimationSet animation = new AnimationSet(false); //change to false
                animation.addAnimation(fadeIn);
                hiddenView.setAnimation(animation);
            }
        });


        flag_ship.setOnClickListener(v -> {
            flag_ship.setShapeType(ShapeType.PRESSED);

            Bundle bundle = new Bundle();
            bundle.putString("eventType", "Flagships");
            Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            flag_ship.setShapeType(ShapeType.FLAT);

        });

        technitude.setOnClickListener(v -> {
            technitude.setShapeType(ShapeType.PRESSED);

            Bundle bundle = new Bundle();
            bundle.putString("eventType", "Events");
            Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            technitude.setShapeType(ShapeType.FLAT);

        });

        github_isa.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/isa-vit"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

//----------------------------------------------------------------------------------------------------------

        isa_instagram.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/isa_vit_/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        isa_github.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/isa-vit"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        isa_linkedin.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.linkedin.com/company/international-society-of-automation-isa-vit/mycompany/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        isa_medium.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://medium.com/isa-vit"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        isa_youtube.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.youtube.com/channel/UCS-HWnmvs5cYEplDuxfO7PA"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.scroller);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Board_Member_Data")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", document.getId() + " => " + document.getData());

                            details = (document.getData());

                            name_list.add((String) details.get("name"));
                            position_list.add((String) details.get("position"));
                            instagram_list.add((String) details.get("instagram_link"));
                            linkedin_list.add((String) details.get("linkedin_link"));
                            photo_list.add((String) details.get("photo_link"));

                            Log.d("TAG", "Links" + name_list.toString());

                            try {
                                Log.d("TAG", "Reached here" + details.toString());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                                recyclerView.setAdapter(new BoardListAdapter(name_list, position_list, instagram_list, linkedin_list, photo_list));

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