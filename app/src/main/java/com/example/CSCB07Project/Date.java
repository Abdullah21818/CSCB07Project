package com.example.CSCB07Project;

import java.util.HashMap;

public class Date {
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;

    public Date(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
        hour = 0;
        minute = 0;
    }

    public Date(int month, int day, int year, int hour, int minute){
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public Date(HashMap<String, Object> data){
        this.month = (int)(long)data.get("month");
        this.day = (int)(long)data.get("day");
        this.year = (int)(long)data.get("year");
        this.hour = (int)(long)data.get("hour");
        this.minute = (int)(long)data.get("minute");
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
