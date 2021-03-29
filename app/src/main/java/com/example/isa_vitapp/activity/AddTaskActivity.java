package com.example.isa_vitapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.example.isa_vitapp.R;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    LinearLayout splash, summary;
    ScrollView task_layout;

    CalendarView calendarView;

    Button set_button, final_create_button, back_button, create_in_splash;
    TextView start_date, end_date, dead_line_message, duration, summary_title, summary_desc, task_desc, task_title, domain_chosen;
    ImageButton full_screen_button;

    RadioGroup tech_non_tech, tech, non_tech;

    LottieAnimationView anim_view;

    String startDate, endDate, domain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Animation animation_fadein, animation_fadeout;

        anim_view = findViewById(R.id.animation_view);
        animation_fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation_fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        splash = findViewById(R.id.create_task_splash);
        task_layout = findViewById(R.id.create_task_layout);
        create_in_splash = findViewById(R.id.create_task_button_splash);
        summary = findViewById(R.id.summary);

        set_button = findViewById(R.id.selectButton);
        final_create_button = findViewById(R.id.final_create_button);
        full_screen_button = findViewById(R.id.full_screen_button);
        back_button = findViewById(R.id.back_button);

        calendarView = findViewById(R.id.cosmo_calendar);

        start_date = findViewById(R.id.start_date_text);
        end_date = findViewById(R.id.end_date_text);
        dead_line_message = findViewById(R.id.deadline_message);
        duration = findViewById(R.id.duration_text);
        domain_chosen = findViewById(R.id.domain_description);

        tech_non_tech = findViewById(R.id.tech_non_tech_group);
        tech = findViewById(R.id.tech_group);
        non_tech = findViewById(R.id.non_tech_group);

        summary_desc = findViewById(R.id.summary_description);
        summary_title = findViewById(R.id.summary_title);

        task_desc = findViewById(R.id.task_description);
        task_title = findViewById(R.id.task_title);

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setCalendarOrientation(0);

        calendarView.setWeekendDays(new HashSet() {{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});

        calendarView.setSelectionType(SelectionType.RANGE);


//Splash Screen Create Button
        create_in_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash.startAnimation(animation_fadeout);
                task_layout.startAnimation(animation_fadein);
                task_layout.setVisibility(View.VISIBLE);
                splash.setVisibility(View.GONE);
            }
        });


//Main Radio Button
        tech_non_tech.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.tech) {
                    tech.setVisibility(View.VISIBLE);
                    non_tech.setVisibility(View.GONE);
                } else if (i == R.id.non_tech) {
                    non_tech.setVisibility(View.VISIBLE);
                    tech.setVisibility(View.GONE);
                }
            }
        });

//Technical Domains RadioButton
        tech.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.app_dev) {
                    domain = "App Dev";
                }

                if (i == R.id.iot) {
                    domain = "IoT";
                }
                if (i == R.id.ml) {
                    domain = "ML";
                }
                if (i == R.id.web_dev) {
                    domain = "Web Dev";
                }
            }
        });


//Non-Technical Domains RadioButton
        non_tech.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.design) {
                    domain = "Design";
                }

                if (i == R.id.events) {
                    domain = "Events";
                }
                if (i == R.id.finance) {
                    domain = "Finance";
                }
                if (i == R.id.content) {
                    domain = "Content";
                }
            }
        });


//Calender Fullscreen button
        full_screen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CalendarDialog(AddTaskActivity.this, new OnDaysSelectionListener() {

                    @Override
                    public void onDaysSelected(List<Day> selectedDays) {
                        int duration_time = 0;
                        try {

                            startDate = String.valueOf(selectedDays.get(0).getDayNumber());
                            endDate = String.valueOf(selectedDays.get(selectedDays.size() - 1).getDayNumber());

                            Toast.makeText(AddTaskActivity.this, selectedDays.toString(), Toast.LENGTH_SHORT).show();

                            Toast.makeText(AddTaskActivity.this, "Day Number : " + selectedDays.get(selectedDays.size() - 1).getDayNumber(), Toast.LENGTH_SHORT).show();

                            start_date.setText(startDate);
                            end_date.setText(endDate);

                            duration_time = Integer.parseInt(endDate) - Integer.parseInt(startDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!selectedDays.isEmpty()) {
                            if (duration_time < 5) {
                                dead_line_message.setText("Seems short! :( ");
                                dead_line_message.setBackgroundResource(R.drawable.button_back_red_fill);

                            } else if (duration_time > 10) {
                                dead_line_message.setText("That's long! :( ");
                                dead_line_message.setBackgroundResource(R.drawable.button_back_yellow_fill);
                            } else {
                                dead_line_message.setText("Seems perfect! :) ");
//                                thumbsup_anim_view.setVisibility(View.VISIBLE);
                                dead_line_message.setBackgroundResource(R.drawable.button_back_green_fill);
                                dead_line_message.setTextColor(Color.parseColor("#121212"));

                            }
                        } else {
                            Toast.makeText(AddTaskActivity.this, "No dates selected!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).show();
            }
        });


//Set Date Button
        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (calendarView.getSelectionManager() instanceof RangeSelectionManager) {

                    RangeSelectionManager rangeSelectionManager = (RangeSelectionManager) calendarView.getSelectionManager();

                    if (rangeSelectionManager.getDays() != null) {

                        startDate = rangeSelectionManager.getDays().first.toString();
                        endDate = rangeSelectionManager.getDays().second.toString();
                        dead_line_message.setText("Seems right! :) ");

                        Toast.makeText(getApplicationContext(), startDate + " " + endDate, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Selection", Toast.LENGTH_SHORT).show();
                    }
                }
                task_layout.startAnimation(animation_fadeout);
                summary.setVisibility(View.VISIBLE);

                summary.startAnimation(animation_fadein);


//Task Details
                try {
                    summary_title.setText(task_title.getText().toString());
                    summary_desc.setText(task_desc.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddTaskActivity.this, "Please fill the task details!", Toast.LENGTH_SHORT).show();
                }


//Domain
                try {
                    domain_chosen.setText("Domain : " + domain);
                } catch (Exception e) {
                    Toast.makeText(AddTaskActivity.this, "Please select a domain!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


//Dates
                try {
                    start_date.setText("Start Date : " + startDate);
                    end_date.setText("End Date : " + endDate);
                    duration.setText("Duration : " + (Integer.parseInt(endDate) - Integer.parseInt(startDate)) + " days");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddTaskActivity.this, "Please set the dates!", Toast.LENGTH_SHORT).show();
                }
            }
        });


//Back button in summary layout
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                task_layout.startAnimation(animation_fadein);
                summary.setVisibility(View.GONE);

                summary.startAnimation(animation_fadeout);
            }
        });


//Create button in summary layout
        final_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    anim_view.setVisibility(View.VISIBLE);
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(AddTaskActivity.this, "Please set the dates!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}