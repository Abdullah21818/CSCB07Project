package com.example.CSCB07Project;

import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList <String> specs;
    //blank appointments with null patients; just fill in doctor
    private ArrayList<Date> timeslots;

    public Doctor(String userId, String password, String name, String gender,
                  ArrayList <String> specs) {
        super(userId, password, name, gender);
        this.specs = specs;
        timeslots = new ArrayList<Date>();
    }

    public Doctor(String userId, String password, String name, String gender,
                  ArrayList<String> visited, ArrayList<Appointment> upcomingAppoint,
                  ArrayList<String> specs, ArrayList<Date> timeslots) {
        super(userId, password, name, gender, visited, upcomingAppoint);
        this.specs = specs;
        this.timeslots = timeslots;
    }

    public ArrayList<String> getSpecs() {
        return specs;
    }

    public ArrayList<Date> getTimeslots() {
        return timeslots;
    }

    public void addSpecialization(String s) {
        specs.add(s);
        FirebaseHelper.updateList(this.getClass().getName(), userId, "specs", specs);
    }

    public void addTimeSlot(Date d) {
        timeslots.add(d);
        FirebaseHelper.updateList(this.getClass().getName(), userId, "timeslots", timeslots);
    }

    public void removeTimeSlot(Date d) {
        timeslots.remove(d);
        FirebaseHelper.updateList(this.getClass().getName(), userId, "timesslots", timeslots);
    }

    @Override
    public void addAppointment(Appointment a) {
        super.addAppointment(a);
        removeTimeSlot(a.getEnd());
    }
}
