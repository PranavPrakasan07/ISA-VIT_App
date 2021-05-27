package com.pranavprksn.isa_vitapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pranavprksn.isa_vitapp.classes.FetchFromDB;
import com.pranavprksn.isa_vitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class NotMemberPage extends AppCompatActivity {

    Button github_isa, flagship_button, events_button, technitudes_button;

    ImageView board_instagram, board_linkedin, board_github, isa_instagram, isa_linkedin, isa_github, isa_medium, isa_youtube, member_image, photos;

    TextView board_member_name, board_member_position;

    Button back_page, front_page;

    LinearLayout main_page, main_layout;

    boolean to_front = true;

    private int increment = 1, counter = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    GradientDrawable gd;

    String[] color_array = {"#FF6363", "#00B2FF", "#FFC700"};
    FetchFromDB[] board_object = new FetchFromDB[FetchFromDB.total_board_members];

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_member_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Handler mHandler = new Handler();

        board_linkedin = findViewById(R.id.linkedin_icon);
        board_github = findViewById(R.id.github_icon);
        board_instagram = findViewById(R.id.instagram_icon);

        isa_linkedin = findViewById(R.id.isa_linkedin_icon);
        isa_github = findViewById(R.id.isa_github_icon);
        isa_instagram = findViewById(R.id.isa_instagram_icon);
        isa_medium = findViewById(R.id.isa_medium_icon);
        isa_youtube = findViewById(R.id.isa_youtube_icon);
        github_isa = findViewById(R.id.github_link);

        board_member_name = findViewById(R.id.member_name);
        board_member_position = findViewById(R.id.position);

        member_image = findViewById(R.id.member_photo);
        photos = findViewById(R.id.photos);

        flagship_button = findViewById(R.id.flagship_event_button);
        events_button = findViewById(R.id.events_button);
        technitudes_button = findViewById(R.id.technitudes_button);

        back_page = findViewById(R.id.back_button);
        front_page = findViewById(R.id.front_button);

        main_page = findViewById(R.id.board_page_layout);
        main_layout = findViewById(R.id.member_prof_layout);

        ArrayList<String> positions = new ArrayList<String>(FetchFromDB.position_name.keySet());

        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackColor(!to_front);
                counter--;

                String name = "";

                Toast.makeText(getApplicationContext(), positions.toString(), Toast.LENGTH_SHORT).show();

                try {
                    name = Objects.requireNonNull(FetchFromDB.position_name.get(positions.get(counter % 12))).toString();
                    Toast.makeText(NotMemberPage.this, Objects.requireNonNull(FetchFromDB.position_name.get(positions.get(counter % 12))).toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DocumentReference docRef = db.collection("Board_Data").document(name);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        board_object[counter] = documentSnapshot.toObject(FetchFromDB.class);

                        Log.d("Inside member : ", board_object[counter].getMember_name());
                        board_member_name.setText(board_object[counter].getMember_name());
                        board_member_position.setText(board_object[counter].getPosition());
                    }
                });
            }
        });

        front_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackColor(to_front);
                counter++;

                String name = "";

                try {
                    name = Objects.requireNonNull(FetchFromDB.position_name.get(positions.get(counter % 12))).toString();
                    Toast.makeText(NotMemberPage.this, name, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DocumentReference docRef = db.collection("Board_Data").document(name);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        board_object[counter] = documentSnapshot.toObject(FetchFromDB.class);

                        Log.d("Inside member : ", board_object[counter].getMember_name());
                        board_member_name.setText(board_object[counter].getMember_name());
                        board_member_position.setText(board_object[counter].getPosition());
                    }
                });
            }
        });

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

//----------------------------------------------------------------------------------------------------------

        isa_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/isa_vit_/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        isa_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/isa-vit"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        isa_linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.linkedin.com/company/international-society-of-automation-isa-vit/mycompany/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        isa_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://medium.com/@isavit_blog"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        isa_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/channel/UCS-HWnmvs5cYEplDuxfO7PA"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//----------------------------------------------------------------------------------------------------------

        flagship_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });

        events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });

        technitudes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MyActivity.class));
            }
        });
    }

    private void setBackColor(boolean to_front) {

        int[] colors_b = {Color.parseColor(color_array[Math.abs((increment - 1) % 3)]), Color.parseColor("#121212")};

        //create a new gradient color
        gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors_b);

        gd.setCornerRadius(0f);
        //apply the button background to newly created drawable gradient
        back_page.setBackground(gd);

        main_page.setBackgroundColor(Color.parseColor(color_array[Math.abs((increment) % 3)]));

        int[] colors_f = {Color.parseColor(color_array[Math.abs((increment + 1) % 3)]), Color.parseColor("#121212")};

        //create a new gradient color
        gd = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors_f);

        gd.setCornerRadius(0f);
        front_page.setBackground(gd);

        if (to_front) {
            increment++;
        } else {
            increment--;
        }
    }

    public void getDetailsForCard(String name) {
        DocumentReference docRef = db.collection("cities").document("SF");

// Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

// Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    Log.d("TAG", "Cached document data: " + document.getData());
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", Objects.requireNonNull(e.getMessage()));
            return null;
        }
    }
}