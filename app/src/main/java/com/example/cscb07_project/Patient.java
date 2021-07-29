package com.example.cscb07_project;

//import java.util.Date;

import com.example.cscb07_project.data.model.User;

public class Patient extends User {
    private int month;
    private int day;
    private int year;


    public Patient(String userId, String name, int month, int day, int year){
        super(userId, name);
        this.month=month;
        this.day=day;
        this.year = year;

    }




}
