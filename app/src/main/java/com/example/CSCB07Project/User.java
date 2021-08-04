package com.example.CSCB07Project;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public abstract class User {
    protected String userId;
    protected String password;
    protected String name;
    protected String gender;
    //Store the userId of the people visited
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

    public User(HashMap<String, Object> data){
        this.userId = (String)data.get("userId");
        this.password = (String)data.get("password");
        this.name = (String)data.get("name");
        this.gender = (String)data.get("gender");
        this.visited = (ArrayList<String>)data.get("visited");

        ArrayList<Appointment> upcomingAppoint = new ArrayList<Appointment>();
        ArrayList<HashMap<String, Object>> allAppointments = (ArrayList<HashMap<String, Object>>)data.get("upcomingAppoint");
        if(allAppointments != null){
            for(HashMap<String, Object> appointment : allAppointments){
                upcomingAppoint.add(new Appointment(appointment));
            }
        }
        this.upcomingAppoint = upcomingAppoint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getVisited() {
        return visited;
    }

    public void setVisited(ArrayList<String> visited) {
        this.visited = visited;
    }

    public ArrayList<Appointment> getUpcomingAppoint() {
        return upcomingAppoint;
    }

    public void setUpcomingAppoint(ArrayList<Appointment> upcomingAppoint) {
        this.upcomingAppoint = upcomingAppoint;
    }

    public void addVisited(String userId) {
        if (!visited.contains(userId)) {
            visited.add(userId);
            FirebaseAPI.updateList(this.getClass().getName() + "s/" + userId +
                    "/visited", visited);
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
        FirebaseAPI.updateList(this.getClass().getName()+ "s/" + userId +
                "/upcomingAppoints", upcomingAppoint);
    }

    public void removeAppointment(Appointment a){
        upcomingAppoint.remove(a);
        FirebaseAPI.updateList(this.getClass().getName() + "s/" + userId +
                "/upcomingAppoints", upcomingAppoint);
    }
}