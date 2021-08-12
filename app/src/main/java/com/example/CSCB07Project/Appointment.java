package com.example.CSCB07Project;

import android.content.Intent;
import android.renderscript.Element;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Appointment {
    //stores the userid of the doctor and patient
    protected String doctor;
    protected String patient;
    protected Date start;
    protected Date end;
    protected boolean passed;

    public Appointment(String doctor, String patient, Date start, Date end) {
        this.doctor = doctor;
        this.patient = patient;
        this.start = start;
        this.end = end;
    }

    public Appointment(String doctor, String patient, Date start, Date end, String UserType, String UserID) {
        this.doctor = doctor;
        this.patient = patient;
        this.start = start;
        this.end = end;
        this.passed = passed;
//        FirebaseAPI.<String>getData(UserType + "/" + UserID + "/login_time", new Callback<String>() {
//            @Override
//            public void onCallback(String data) {
//                if(password.equals(data)) {
//                    java.util.Date date = new java.util.Date(System.currentTimeMillis());
//                    DatabaseReference P_ref = FirebaseDatabase.getInstance().getReference().child("Patients");
//                    java.util.Date time = new java.util.Date(System.currentTimeMillis());
//                }
//            }
//        });
        }

    public Appointment(HashMap<String, Object> data){
        this.doctor = (String)data.get("doctor");
        this.patient = (String)data.get("patient");
        this.start = new Date((HashMap<String, Object>)data.get("start"));
        this.end = new Date((HashMap<String, Object>)data.get("end"));
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        String msg = "Started at: " + this.start.toString() + "\n" + "Ended at" + this.end.toString() +
                "Doctor: " + this.doctor + "\n" + "Patience: " + this.patient;
        return msg;
    }
}
