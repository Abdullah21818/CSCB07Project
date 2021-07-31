package com.example.CSCB07Project;

import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList <String> specs;
    //blank appointments with null patients; just fill in doctor
    private ArrayList<Appointment> timeslots;

    public Doctor(String userId, String password, String name, String gender) {
        super(userId, password, name, gender);
        specs = new ArrayList<String>();
        timeslots = new ArrayList<Appointment>();
    }

    public void addSpecialization(String s) {
        specs.add(s);
    }

    public void addTimeslot(Date start, Date end) {
        timeslots.add(new Appointment(this, null, start, end));
    }

    public void setAppointment(Appointment a) {
        upcomingAppoint.add(a);
    }
}
