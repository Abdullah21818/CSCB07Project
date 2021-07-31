package com.example.CSCB07Project;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public abstract class User {
    protected String userId;
    protected String password;

    protected String name;
    protected String gender;
    protected LinkedHashSet<User> visited;
    protected ArrayList<Appointment> upcomingAppoint;

    public User(String userId, String password, String name, String gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        visited = new LinkedHashSet<User>();
        upcomingAppoint = new ArrayList<Appointment>();
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

    public ArrayList<Appointment> getUpcomingAppoint() {
        return upcomingAppoint;
    }

    @Override
    public int hashCode(){
        return userId.hashCode();
    }
}