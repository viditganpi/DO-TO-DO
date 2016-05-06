package com.example.vidit.do_to_do;

/**
 * Created by vidit on 05-05-2016.
 */
public class Task {
    private String title;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private String location;
    public Task(){}
    public Task(String  title,int hour,int minute,int year,int month,int day,String location){
        this.title = title;
        this.hour=hour;
        this.minute=minute;
        this.year=year;
        this.month=month;
        this.day=day;
        this.location = location;
        //this.second=second;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
