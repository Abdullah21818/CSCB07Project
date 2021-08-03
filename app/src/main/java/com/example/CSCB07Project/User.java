package com.example.CSCB07Project;

import java.util.ArrayList;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public abstract class User {
    protected String userId;
    protected String password;

    protected String name;
    protected String gender;
    //Store the userId instead
    protected ArrayList<String> visited;
    protected ArrayList<Appointment> upcomingAppoint;

    public User(String userId, String password, String name, String gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        visited = new ArrayList<String>();
        upcomingAppoint = new ArrayList<Appointment>();
    }

    public User(String userId, String password, String name, String gender,
                ArrayList<String> visited, ArrayList<Appointment> upcomingAppoint) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.visited = visited;
        this.upcomingAppoint = upcomingAppoint;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void addVisited(String userId) {
        if (!visited.contains(userId)) {
            visited.add(userId);
            FirebaseAPI.updateList(this.getClass().getName(), userId,
                                "visited", visited);
        }
    }

    public ArrayList<Appointment> getAllUpcomingAppoint() {
        return upcomingAppoint;
    }

    @Override
    public int hashCode(){
        return userId.hashCode();
    }

    public void addAppointment(Appointment a) {
        upcomingAppoint.add(a);
        FirebaseAPI.updateList(this.getClass().getName(), userId,
                            "upcomingAppoints", upcomingAppoint);
    }

    public void removeAppointment(Appointment a){
        upcomingAppoint.remove(a);
        FirebaseAPI.updateList(this.getClass().getName(), userId,
                            "upcomingAppoints", upcomingAppoint);
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", userId, name, password);
    }
}