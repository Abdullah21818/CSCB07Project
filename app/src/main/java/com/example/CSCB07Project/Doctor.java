package com.example.CSCB07Project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Doctor extends User {
    private ArrayList<String> specs;
    //blank appointments with null patients; just fill in doctor
    private ArrayList<Date> timeslots;

    public Doctor() {}

    public Doctor(String userId, String password, String name, String gender) {
        super(userId, password, name, gender);
        timeslots = new ArrayList<Date>();
    }

    public Doctor(String userId, String password, String name, String gender,
                  ArrayList<String> specs) {
        super(userId, password, name, gender);
        this.specs = specs;
        this.timeslots = new ArrayList<Date>();
    }

    public Doctor(String userId, String password, String name, String gender,
                  ArrayList<String> visited, ArrayList<Appointment> upcomingAppoint,
                  ArrayList<String> specs, ArrayList<Date> timeslots) {
        super(userId, password, name, gender, visited, upcomingAppoint);
        this.specs = specs;
        this.timeslots = timeslots;
    }

    public Doctor(HashMap<String, Object> data){
        super(data);
        this.specs = (ArrayList<String>)data.get("specs");
        ArrayList<Date> timeslots = new ArrayList<Date>();
        ArrayList<HashMap<String, Object>> allTimeSlots = (ArrayList<HashMap<String, Object>>)data.get("timeslots");
        if(allTimeSlots != null){
            for(HashMap<String, Object> date : allTimeSlots){
                timeslots.add(new Date(date));
            }
        }
        this.timeslots = timeslots;
    }

    public ArrayList<String> getSpecs() {
        return specs;
    }

    public ArrayList<Date> getTimeslots() {
        return timeslots;
    }

    public void addSpecialization(String s) {
        if(specs.add(s))
            FirebaseAPI.updateList( "Doctors/" + userId + "/specs", specs);
    }

    public void removeSpecialization(String s) {
        if(specs.remove(s))
            FirebaseAPI.updateList( "Doctors/" + userId + "/specs", specs);
    }

    public void addTimeSlot(Date d) {
        if(timeslots.add(d))
            FirebaseAPI.updateList("Doctors/" + userId + "/timeslots", timeslots);
    }

    public void removeTimeSlot(Date d) {
        if(timeslots.remove(d))
            FirebaseAPI.updateList("Doctors/" + userId + "/timesslots", timeslots);
    }

    @Override
    public void addAppointment(Appointment a) {
        super.addAppointment(a);
        removeTimeSlot(a.getEnd());
    }

    @Override
    public void removeAppointment(Appointment a) {
        super.removeAppointment(a);
        addTimeSlot(a.getEnd());
    }
}
