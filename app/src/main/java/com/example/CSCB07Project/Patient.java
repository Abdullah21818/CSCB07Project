package com.example.CSCB07Project;

import java.util.ArrayList;
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
    public void addAppointment(Appointment a) {
        super.addAppointment(a);
        FirebaseAPI.getDoctor(a.doctor, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);
                doctor.addAppointment(a);
            }
        });
    }

    @Override
    public void uploadToFirebase() {
        FirebaseAPI.uploadData("Patients/" + userId + "/upcomingAppointments", upcomingAppointments);
    }
}
