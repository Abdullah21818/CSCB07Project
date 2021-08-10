package com.example.CSCB07Project;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Date {
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;

    public Date(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

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

    public static Date getCurrentTime(){
        Calendar c = new GregorianCalendar();
        return new Date(c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.YEAR), c.get(Calendar.HOUR), c.get(Calendar.MINUTE));
    }

    public boolean sameDay(Date date){
        return date.getDay() == day && date.getMonth() == month && date.getYear() == year;
    }

    public boolean beforeThis(Date date){
        return date.getYear() < year || (date.getYear() == year && date.getMonth() < month) ||
                (date.getYear() == year && date.getMonth() == month && date.getDay() < day) ||
                (date.getYear() == year && date.getMonth() == month && date.getDay() == day &&
                        date.getHour() < hour) ||
                (date.getYear() == year && date.getMonth() == month && date.getDay() == day &&
                        date.getHour() == hour && date.getMinute() < minute);
    }

    @Override
    public String toString() {
        String h = Integer.toString(hour);
        String m = Integer.toString(minute);
        if (hour == 0) {
            h = "00";
        }
        if (minute == 0) {
            m = "00";
        }

        return month + "/" + day + "/" + year + " \t" + h + ":" + m;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass()){
            return false;
        }
        Date date = (Date)o;
        return date.getDay() == day && date.getHour() == hour && date.getMinute() == minute
                && date.getMonth() == month && date.getYear() == year;
    }

    @Override
    public int hashCode(){
        return minute*60 + hour*360 + day*8640 + month*10000 + year;
    }
}
