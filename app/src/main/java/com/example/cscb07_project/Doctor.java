package com.example.cscb07_project;

import com.example.cscb07_project.data.model.User;

import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList <String> specs;
    //Remember to add weekly availabilities or timeslots

    public Doctor(String userId, String name, String gender) {
        super(userId, name, gender);
    }

    public void setAppointment(Appointment a) {
        upcomingAppoint.add(a);
    }
}
