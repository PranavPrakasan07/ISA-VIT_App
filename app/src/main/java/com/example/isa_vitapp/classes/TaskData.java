package com.example.isa_vitapp.classes;

public class TaskData {

    String title;
    String deadline;
    String setby;
    String description;
    Boolean passed;

    public static String task_title1 = "";
    public static String task_deadline1 = "";
    public static String task_description1 = "";
    public static Boolean task_passed1 = false;

    public static String task_title2 = "";
    public static String task_deadline2 = "";
    public static String task_description2 = "";
    public static Boolean task_passed2 = false;

    public TaskData() {
        this.title = "title";
        this.deadline = "deadline";
        this.setby = "setby";
        this.description = "description";
        this.passed = false;
    }

    public TaskData(String title, String deadline, String setby, String description, Boolean passed) {
        this.title = title;
        this.deadline = deadline;
        this.setby = setby;
        this.description = description;
        this.passed = passed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSetby() {
        return setby;
    }

    public void setSetby(String setby) {
        this.setby = setby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
