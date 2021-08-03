package com.example.CSCB07Project;

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

    @Override
    public String toString() {
        return "Date (MM/DD/YYYY): \t" + month + "/" + day + "/" + year
                + "\nTime: \t" + hour + ":" + minute;
    }
}
