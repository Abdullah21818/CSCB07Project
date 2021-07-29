package com.example.cscb07_project;

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
    }

    public Date(int month, int day, int year, int hour, int minute){
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }
}
