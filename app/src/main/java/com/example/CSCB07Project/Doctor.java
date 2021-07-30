package com.example.CSCB07Project;

import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList <String> specs;
    //Remember to add weekly availabilities or timeslots
    //we can make blank appointments with null patients, and just fill in name

    public Doctor(String userId, String password, String name, String gender) {
        super(userId, password, name, gender);
    }

    public void setAppointment(Appointment a) {
        upcomingAppoint.add(a);
    }
}
