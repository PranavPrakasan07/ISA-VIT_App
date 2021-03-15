package com.example.isa_vitapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.isa_vitapp.FetchFromDB;
import com.example.isa_vitapp.MyActivity;
import com.example.isa_vitapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotMemberPage extends AppCompatActivity {

    Button github_isa, flagship_button, events_button, technitudes_button;
    ImageView member_image, photos;

    Button back_page, front_page;

    LinearLayout main_page, main_layout;

    boolean to_front = true;

    private String left_color, center_color, right_color;
    private int increment = 1;
    int counter = 0;

    GradientDrawable gd;

    String[] color_array = {"#FF6363", "#00B2FF", "#FFC700"};

//    int[] colors_b = new int[2];
//    int[] colors_f = new int[2];

//    private SimpleGestureFilter detector;
//
//    public OnSwipeTouchListener onSwipeTouchListener;


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

//
//        FetchFromDB names = new FetchFromDB();
//
//        final Map[] position_name = new Map[]{new HashMap<>()};

//
//        Thread store_names_thread = new Thread() {
//            public void run() {
//                DocumentReference docRef = db.collection("Board").document();
//                docRef.get().
//
//                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    DocumentSnapshot document = task.getResult();
//                                    if (document != null) {
//
////                        Toast.makeText(NotMemberPage.this, Objects.requireNonNull(document.getData()).toString(), Toast.LENGTH_SHORT).show();
//                                        board_members_list.add(Objects.requireNonNull(document.getData()).toString());
//
//                                    } else {
//                                        Log.d("LOGGER", "No such document");
//                                    }
//                                } else {
//                                    Log.d("LOGGER", "get failed with ", task.getException());
//                                }
//                            }
//                        });
//            }
//        };
//
//        try {
//            store_names_thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        github_isa = findViewById(R.id.github_link);
        member_image = findViewById(R.id.member_photo);
        photos = findViewById(R.id.photos);

        flagship_button = findViewById(R.id.flagship_event_button);
        events_button = findViewById(R.id.events_button);
        technitudes_button = findViewById(R.id.technitudes_button);

        back_page = findViewById(R.id.back_button);
        front_page = findViewById(R.id.front_button);

        main_page = findViewById(R.id.board_page_layout);
        main_layout = findViewById(R.id.member_prof_layout);

//        detector = new SimpleGestureFilter(NotMemberPage.this, this);
//        onSwipeTouchListener = new OnSwipeTouchListener(getApplicationContext(), main_layout);

//        green : 73D69B
//        blue : 00B2FF
//        red : FF6363
//        yellow : FFC700


        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackColor(!to_front);
                counter--;
                //getData(counter);

//                Toast.makeText(getApplicationContext(), position_name[0].entrySet().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), position_name[0].values().toString(), Toast.LENGTH_SHORT).show();

//                String url = "https://firebasestorage.googleapis.com/v0/b/isa-vit.appspot.com/o/Board%20Members%2Fvatsal.png?alt=media&token=3c6d40e7-5ae5-42f3-ae00-e53ebc3d3c6c";
//                member_image.setImageBitmap(getBitmapFromURL(url));

                Picasso
                        .get()
                        .load("https://firebasestorage.googleapis.com/v0/b/isa-vit.appspot.com/o/Board%20Members%2Fvatsal.png?alt=media&token=3c6d40e7-5ae5-42f3-ae00-e53ebc3d3c6c")
                        .into(member_image);
            }
        });

        front_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackColor(to_front);
                counter++;
                //getData(counter);


//                Toast.makeText(getApplicationContext(), position_name[0].entrySet().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), position_name[0].values().toString(), Toast.LENGTH_SHORT).show();

                try {
                    Toast.makeText(NotMemberPage.this, FetchFromDB.position_name.get("Chair").toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Picasso
                        .get()
                        .load("https://firebasestorage.googleapis.com/v0/b/isa-vit.appspot.com/o/Board%20Members%2Fpranav.png?alt=media&token=e5ea2646-2626-4b42-8461-9b1f35507a13")
                        .into(member_image);
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