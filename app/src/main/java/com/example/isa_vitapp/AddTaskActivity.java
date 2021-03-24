package com.example.isa_vitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    Button create_in_splash, tutorial_button;
    LinearLayout splash, task_layout;

    CalendarView calendarView;
    Button set_button;
    ImageButton full_screen_button;
    String startDate, endDate;

    RadioGroup tech_non_tech, tech, non_tech;

    TextView start_date, end_date, dead_line_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Animation animation_fadein, animation_fadeout;

        animation_fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation_fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        splash = findViewById(R.id.create_task_splash);
        task_layout = findViewById(R.id.create_task_layout);
        create_in_splash = findViewById(R.id.create_task_button_splash);

        set_button = findViewById(R.id.selectButton);
        full_screen_button = findViewById(R.id.full_screen_button);

        calendarView = findViewById(R.id.cosmo_calendar);

        start_date = findViewById(R.id.start_date_text);
        end_date = findViewById(R.id.end_date_text);
        dead_line_message = findViewById(R.id.deadline_message);

        tech_non_tech = findViewById(R.id.tech_non_tech_group);
        tech = findViewById(R.id.tech_group);
        non_tech = findViewById(R.id.non_tech_group);

        create_in_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash.startAnimation(animation_fadeout);
                task_layout.startAnimation(animation_fadein);
                task_layout.setVisibility(View.VISIBLE);
            }
        });

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

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setCalendarOrientation(0);

        calendarView.setWeekendDays(new HashSet() {{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});

        calendarView.setSelectionType(SelectionType.RANGE);

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getSelectionManager() instanceof RangeSelectionManager) {
                    RangeSelectionManager rangeSelectionManager = (RangeSelectionManager) calendarView.getSelectionManager();
                    if (rangeSelectionManager.getDays() != null) {
                        startDate = rangeSelectionManager.getDays().first.toString();
                        endDate = rangeSelectionManager.getDays().second.toString();
//
//                        start_date.setText(startDate);
//                        end_date.setText(endDate);
//
                        dead_line_message.setText("Seems right! :) ");

                        Toast.makeText(getApplicationContext(), startDate + " " + endDate, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Selection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        full_screen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CalendarDialog(AddTaskActivity.this, new OnDaysSelectionListener() {
                    @Override
                    public void onDaysSelected(List<Day> selectedDays) {
                        Toast.makeText(AddTaskActivity.this, selectedDays.toString(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(AddTaskActivity.this, "Day Number : " + selectedDays.get(selectedDays.size() - 1).getDayNumber(), Toast.LENGTH_SHORT).show();

                        int start_day_number = selectedDays.get(0).getDayNumber();
                        int end_day_number = selectedDays.get(selectedDays.size() - 1).getDayNumber();

                        start_date.setText(String.valueOf(start_day_number));
                        end_date.setText(String.valueOf(end_day_number));
                        end_date.setBackgroundResource(R.drawable.button_back_red_fill);

                        int duration = end_day_number - start_day_number;

                        if (duration < 5) {
                            dead_line_message.setText("Seems short! :( ");
                            dead_line_message.setBackgroundResource(R.drawable.button_back_red_fill);

                        } else if (duration > 10) {
                            dead_line_message.setText("That's long! :( ");
                            dead_line_message.setBackgroundResource(R.drawable.button_back_yellow_fill);
                        } else {
                            dead_line_message.setText("Seems perfect! :) ");
                            dead_line_message.setBackgroundResource(R.drawable.button_back_green_fill);
                            dead_line_message.setTextColor(Color.parseColor("#121212"));
                        }

                    }
                }).show();
            }
        });

    }
}