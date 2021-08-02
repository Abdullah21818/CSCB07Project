package com.example.CSCB07Project;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    //used to retrieve data from Firebase
    private static String tempString;
    private static boolean exists;
    public static String getValue(DatabaseReference ref){
        exists = false;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    exists = true;
                    Object value = snapshot.getValue();
                    if(value != null)
                        tempString = value.toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        if(exists)
            return tempString;
        return null;
    }

    public static Date getDate(String className, String userId, String key){
        String databaseName = className + "s";
        DatabaseReference ref = FirebaseDatabase.getInstance()
                                .getReference().child(databaseName).child(userId).child(key);
        int month = Integer.parseInt(getValue(ref.child("month")));
        int day = Integer.parseInt(getValue(ref.child("day")));
        int year = Integer.parseInt(getValue(ref.child("year")));
        int hour = Integer.parseInt(getValue(ref.child("hour")));
        int minute = Integer.parseInt(getValue(ref.child("minute")));

        return new Date(month, day, year, hour, minute);
    }

    //used to retrieve data from Firebase
    private static ArrayList<String> tempList;
    public static ArrayList<String> getList(DatabaseReference ref){
        tempList = new ArrayList<String>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()){
                    tempList.add(d.getValue().toString());
                }
                tempString = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        return tempList;
    }

    public static ArrayList<Date> getDateList(DatabaseReference ref){
        //ArrayList<String> StringList = getList
        return new ArrayList<Date>();
    }

    public static ArrayList<Appointment> getAppointmentList(DatabaseReference ref){
        //ArrayList<String> StringList = getList
        return new ArrayList<Appointment>();
    }

    public static Doctor getDoctor(String className, String userId){
        String databaseName = className + "s";
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child(databaseName).child(userId);

        String password = getValue(ref.child("password"));
        String name = getValue(ref.child("name"));
        String gender = getValue(ref.child("gender"));
        ArrayList<String> visited = getList(ref.child("visited"));
        ArrayList<Appointment> upcomingAppoint = getAppointmentList(ref.child("upcomingAppoint"));
        ArrayList<String> specs = getList(ref.child("specs"));
        ArrayList<Date> timeslots = getDateList(ref.child("timeslots"));
        return new Doctor(userId, password, name, gender, visited,
                            upcomingAppoint, specs, timeslots);
    }

    public static void updateList(String className, String userId, String listName, List list){
        String databaseName = className + "s";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(databaseName).child(userId).child(listName).setValue(list);
    }
}
