package com.example.CSCB07Project;

import java.util.HashMap;

public class Appointment {
    //stores the userid of the doctor and patient
    protected String doctor;
    protected String patient;
    protected Date start;
    protected Date end;

    public Appointment(String doctor, String patient, Date start, Date end) {
        this.doctor = doctor;
        this.patient = patient;
        this.start = start;
        this.end = end;
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
}
