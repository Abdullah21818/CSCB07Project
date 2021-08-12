package com.example.CSCB07Project;

import android.util.Log;

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

    public static boolean checkValid(Date date){
        boolean valid = true;
        try{
            Calendar c = Calendar.getInstance();
            c.setLenient(false);
            c.set(date.getYear(), date.getMonth()-1, date.getDay());
            //c.setTime(new java.util.Date(date.getYear(), date.getMonth(), date.getDay()));
            c.getTime();
        } catch(Exception e){
            valid = false;
        }
        return valid;
    }

    public boolean isWorkDay(){
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day);
        Log.i("day of week",c.get(Calendar.DAY_OF_WEEK)+"" );
        return c.get(Calendar.DAY_OF_WEEK) > 1 && c.get(Calendar.DAY_OF_WEEK) < 7;
    }

    public boolean sameDay(Date date){
        return date.getDay() == day && date.getMonth() == month && date.getYear() == year;
    }

    public boolean after(Date date){
        Calendar c = new GregorianCalendar();
        c.set(year, month-1, day, hour, minute);
        long value = c.getTimeInMillis();
        c.set(date.getYear(), date.getMonth()-1, date.getDay(), date.getHour(), date.getMinute());
        long dateValue = c.getTimeInMillis();

        return value > dateValue;
    }

    public void ToNextSunday(){
        Calendar c = new GregorianCalendar();
        c.set(year, month - 1, day);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_YEAR,8-dayOfWeek);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
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

        return month + "/" + day + "/" + year + "\t" + h + ":" + m;
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
