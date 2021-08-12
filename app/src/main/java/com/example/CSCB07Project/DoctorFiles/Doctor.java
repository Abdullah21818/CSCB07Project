package com.example.CSCB07Project.DoctorFiles;

import com.example.CSCB07Project.Appointment;
import com.example.CSCB07Project.Date;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctor extends User {
    private ArrayList<String> specs;
    //blank appointments with null patients; just fill in doctor
    private ArrayList<Date> timeslots;

    public Doctor(String userId, String password, String name, String gender) {
        super(userId, password, name, gender);
    }

    public Doctor(String userId, String password, String name, String gender,
                  ArrayList<String> specs, ArrayList<Date> timeslots) {
        super(userId, password, name, gender);
        this.specs = specs;
        this.timeslots = timeslots;
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

    @Override
    public void addVisited(String username) {
        if (!visited.contains(username)) {
            visited.add(username);
            FirebaseAPI.uploadData("Doctors/" + userId + "/visited", visited);
        }
    }

    @Override
    public boolean addAppointment(Appointment a) {
        if(!upcomingAppointments.contains(a)) {
            upcomingAppointments.add(a);
            FirebaseAPI.uploadData("Doctors/" + userId + "/upcomingAppointments", upcomingAppointments);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAppointment(Appointment a){
        if(upcomingAppointments.remove(a)) {
            FirebaseAPI.uploadData("Doctors/" + userId + "/upcomingAppointments",
                    upcomingAppointments);
            return true;
        }
        return false;
    }

    public ArrayList<String> getSpecs() {
        return specs;
    }

    public ArrayList<Date> getTimeslots() {
        return timeslots;
    }

    public void addSpecialization(String s) {
        if(specs.add(s))
            FirebaseAPI.uploadData( "Doctors/" + userId + "/specs", specs);
    }

    public void removeSpecialization(String s) {
        if(specs.remove(s))
            FirebaseAPI.uploadData( "Doctors/" + userId + "/specs", specs);
    }
}
