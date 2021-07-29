package com.example.cscb07_project;

//import java.util.Date;

import com.example.cscb07_project.data.model.User;

public class Patient extends User {
    private Date birthday;

    public Patient(String userId, String name, String gender, int month, int day, int year){
        super(userId, name, gender);
        birthday = new Date(month, day, year);
    }

    public void setAppointment(Appointment a) {
        upcomingAppoint.add(a);
        a.doctor.setAppointment(a);
    }
}
