package com.example.CSCB07Project;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseAPI {
    /**
     * This function retrieves data from firebase and execute codes that requires the data
     * @param path - the directory of the data from firebase's root
     * @param c - class that implements Callback
     * @param <DataType> - Classes that are limited to String, bool,
     */
    public static <DataType> void getData (String path, Callback c){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ValueEventListener l = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<DataType> t = new GenericTypeIndicator<DataType>() {};
                if(snapshot.exists()) {
                    DataType data = snapshot.getValue(t);
                    //Log.i("Info", data.toString());
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
        ref.addValueEventListener(l);
    }

    public static void getDoctor (String username, Callback<HashMap<String, Object>> c) {
        FirebaseAPI.<Doctor>getData("Doctors/"+username, c);
    }

    public static void getPatient (String username, Callback<HashMap<String, Object>> c) {
        FirebaseAPI.<Patient>getData("Patients/"+username, c);
    }

    public static void uploadData (String path, Object data){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.setValue(data);
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
        ref.addValueEventListener(l);
    }

    /*
    public static <DataType> void getData(DatabaseReference ref, String path, Callback c){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = true;
                DataSnapshot s = snapshot;
                String[] allPath = path.split("/");
                for(String child: allPath){
                    if(!s.hasChild(child)) {
                        exist = false;
                        break;
                    }
                    s = s.child(child);
                }
                if(exist) {
                    GenericTypeIndicator<DataType> t = new GenericTypeIndicator<DataType>() {};
                    c.<DataType>onCallback(s.getValue(t));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }*/
/*
    public Date getDate(String path){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Date d = null;
        try {
            getValue(ref, (DataType) d);
        }catch (Exception e){
            Log.d("Error: ", "Doctor not found");
        }
        return d;
    }

    //used to retrieve data from Firebase
    private ArrayList<DataType> tempList;
    public ArrayList<DataType> getList(DatabaseReference ref){
        tempList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<DataType> t = new GenericTypeIndicator<DataType>() {};
                for(DataSnapshot d : snapshot.getChildren()){
                    tempList.add(d.getValue(t));
                    Log.i("list: ", tempList.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        return tempList;
    }

    public ArrayList<Date> getDateList(DatabaseReference ref){
        ArrayList<Date> d = null;
        try {
            getValue(ref, (DataType) d);
        }catch (Exception e){
            Log.d("Error: ", "Doctor not found");
        }
        return d;
    }

    public ArrayList<Appointment> getAppointmentList(DatabaseReference ref){
        ArrayList<Appointment> d = null;
        try {
            d = (ArrayList<Appointment>) getValue(ref);
        }catch (Exception e){
            Log.d("Error: ", "Doctor not found");
        }
        return d;
    }

    public Doctor getDoctor(String userId){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("Doctors").child(userId);
        Doctor d = null;
        try {
            d = (Doctor) getValue(ref);
            Log.i("Dattta: ", d.toString());
        }catch (Exception e){
            Log.d("Error: ", "Doctor not found");
        }
        return d;
    }

    public Patient getPatient(String userId){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("Patients").child(userId);
        Patient d = null;
        try {
            d = (Patient) getValue(ref);
        }catch (Exception e){
            Log.d("Error: ", "Patient not found");
        }
        return d;
    }*/
}
