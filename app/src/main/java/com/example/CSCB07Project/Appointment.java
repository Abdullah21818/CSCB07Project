package com.example.CSCB07Project;

public class Appointment {
    protected Doctor doctor;
    protected Patient patient;
    protected Date start;
    protected Date end;

    public Appointment(Doctor doctor, Patient patient, Date start, Date end) {
        this.doctor = doctor;
        this.patient = patient;
        this.start = start;
        this.end = end;
    }
}
