package com.example.CSCB07Project.PatientFiles;

import com.example.CSCB07Project.Appointment;
import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.Date;
import com.example.CSCB07Project.DoctorFiles.Doctor;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.User;

import java.util.HashMap;

public class Patient extends User {
    private Date birthday;

    public Patient(String userId, String password, String name, String gender, Date birthday) {
        super(userId, password, name, gender);
        this.birthday = birthday;
    }

    public Patient(HashMap<String, Object> data){
        super(data);
        this.birthday = new Date((HashMap<String, Object>)data.get("birthday"));
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void addVisited(String username) {
        if (!visited.contains(username)) {
            visited.add(username);
            FirebaseAPI.uploadData("Patients/" + userId + "/visited", visited);
        }
    }

    @Override
    public boolean addAppointment(Appointment a) {
        if(!upcomingAppointments.contains(a)) {
            upcomingAppointments.add(a);
            FirebaseAPI.uploadData("Patients/" + userId + "/upcomingAppointments",
                                    upcomingAppointments);
            FirebaseAPI.getDoctor(a.getDoctor(), new Callback<HashMap<String, Object>>() {
                @Override
                public void onCallback(HashMap<String, Object> data) {
                    Doctor doctor = new Doctor(data);
                    doctor.addAppointment(a);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAppointment(Appointment a){
        if(upcomingAppointments.remove(a)) {
            FirebaseAPI.uploadData("Patients/" + userId + "/upcomingAppointments",
                    upcomingAppointments);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Username: \t" + userId + "\n" + "Name: \t" + name + "\n" + "Gender: \t" + gender
                + "\n" + birthday;
    }
}
