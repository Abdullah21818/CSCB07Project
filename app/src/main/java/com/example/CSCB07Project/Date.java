package com.example.CSCB07Project;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Date {
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;

    public Date() {}

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

    public Date(java.util.Date date){
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        java.util.Date d1=new java.util.Date(time);
        String t1=format.format(d1);
        String[] parts = t1.split("-");
        this.year = Integer.parseInt(parts[0]);
        this.month = Integer.parseInt(parts[1]);
        this.day= Integer.parseInt(parts[2]);
        this.hour = Integer.parseInt(parts[3]);
        this.minute = Integer.parseInt(parts[4]);
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

    public boolean before(Date date){
        if (this.year < date.year){
            return true;
        }
        else if (this.month < date.month){
            return true;
        }
        else if (this.day < date.day){
            return true;
        }
        else if (this.hour < date.hour){
            return true;
        }
        else if (this.minute < date.minute){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year + "\t" + hour + ":" + minute;
    }
}
