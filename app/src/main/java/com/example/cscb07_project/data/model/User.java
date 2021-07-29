package com.example.cscb07_project.data.model;

import com.example.cscb07_project.Appointment;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public abstract class User {

    protected String userId;

    protected String name;
    protected String gender;
    protected LinkedHashSet<User> visited;
    protected ArrayList<Appointment> upcomingAppont;

//    public User(){
//
//    }


    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        visited= new LinkedHashSet<User>();
        upcomingAppont = new ArrayList<Appointment>();
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int hashCode(){
        return userId.hashCode();
    }


}