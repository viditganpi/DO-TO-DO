package com.example.vidit.do_to_do;

/**
 * Created by vidit on 05-05-2016.
 */
public class Task {
    private String title;
    private int hour;
    private int minute;
    public Task(){}
    public Task(String  title,int hour,int minute){
        this.title = title;
        this.hour=hour;
        this.minute=minute;
        //this.second=second;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
