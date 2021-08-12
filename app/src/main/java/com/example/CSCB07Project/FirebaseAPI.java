package com.example.CSCB07Project;

import android.util.Log;

import com.example.CSCB07Project.DoctorFiles.Doctor;
import com.example.CSCB07Project.PatientFiles.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseAPI {
    public FirebaseAPI() {
    }

    public static <DataType> void getData (String path, Callback c){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ValueEventListener l = createValueEventListener(c);
        ref.addListenerForSingleValueEvent(l);
    }

    public static <DataType> void getUpdatingData (String path, Callback c){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ValueEventListener l = createValueEventListener(c);
        ref.addValueEventListener(l);
    }

    private static <DataType> ValueEventListener createValueEventListener(Callback c) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<DataType> t = new GenericTypeIndicator<DataType>() {
                };
                if (snapshot.exists()) {
                    DataType data = snapshot.getValue(t);
                    try {
                        c.onCallback(data);
                    } catch (Exception e) {
                        Log.d("Error", "Invalid data type");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
    }

    public static void getDoctor (String userId, Callback<HashMap<String, Object>> c) {
        FirebaseAPI.<Doctor>getData("Doctors/"+userId, c);
    }

    public static void getPatient (String userId, Callback<HashMap<String, Object>> c) {
        FirebaseAPI.<Patient>getData("Patients/"+userId, c);
    }

    public static void uploadData (String path, Object data){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        for(String child : path.split("/")){
            ref = ref.child(child);
        }
        ref.setValue(data);
    }

    public static void deleteAppointment(Appointment a) {
        getDoctor(a.getDoctor(), new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);
                doctor.removeAppointment(a);
                doctor.addVisited(a.getPatient());
            }
        });
        getPatient(a.getPatient(), new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Patient patient = new Patient(data);
                patient.removeAppointment(a);
                patient.addVisited(a.getDoctor());
            }
        });
    }

    public static void updateDoctorAppointment(String userId){
        FirebaseAPI.getUpdatingData("Doctors/"+userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);
                for (Appointment a : doctor.getUpcomingAppointments()) {
                    if(Date.getCurrentTime().after(a.getEnd())){
                        FirebaseAPI.deleteAppointment(a);
                        return;
                    }
                }
            }
        });
    }

    public static void updatePatientAppointment(String userId){
        FirebaseAPI.getUpdatingData("Patients/"+userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Patient patient = new Patient(data);
                for (Appointment a : patient.getUpcomingAppointments()) {
                    if(Date.getCurrentTime().after(a.getEnd())){
                        FirebaseAPI.deleteAppointment(a);
                        return;
                    }
                }
            }
        });
    }

    public static void getAllUsername(String path, Callback<ArrayList<String>> c){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ValueEventListener l = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    ArrayList<String> usernames = new ArrayList<String>();
                    for(DataSnapshot s : snapshot.getChildren())
                        usernames.add(s.child("userId").getValue(String.class));
                    c.onCallback(usernames);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        ref.addListenerForSingleValueEvent(l);
    }

    public static void getAllPasswords(String path, Callback<ArrayList<String>> c){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ValueEventListener l = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    ArrayList<String> passwords = new ArrayList<String>();
                    for(DataSnapshot s : snapshot.getChildren())
                        passwords.add(s.child("password").getValue(String.class));
                    c.onCallback(passwords);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        ref.addListenerForSingleValueEvent(l);
    }
}
