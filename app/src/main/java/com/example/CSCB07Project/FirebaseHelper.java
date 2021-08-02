package com.example.CSCB07Project;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for retrieving data from the Firebase
 */
public class FirebaseHelper {
    //used to retrieve data from Firebase
    private static Object tempData;

    /**
     * This function gets the value in the path of the reference
     * @param ref - the database reference
     * @param <T> - generic data type required to be returned
     * @return the value in the path of ref
     */
    public static <T> T getValue(DatabaseReference ref){

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    GenericTypeIndicator<T> t = new GenericTypeIndicator<T>() {};
                    tempData = snapshot.getValue(t);
                    Log.i("Data: ", tempData.toString());
                } catch(Exception e){
                    Log.d("Error: ", "Data not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        //T result = (T)tempData;
        //if(result == null)
            //Log.i("exist?: ", "no");
        return (T)tempData;
    }

    /**
     * This function gets the value in the path of the reference,
     * used when not certain if the path exists or not
     * @param ref - the database reference
     * @param path - the path after the ref
     * @param <T> - generic data type required to be returned
     * @return the value in the path of ref, null if path does not exist
     */
    public static <T> T getValue(DatabaseReference ref, String path){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = true;
                tempData = null;
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
                    GenericTypeIndicator<T> t = new GenericTypeIndicator<T>() {};
                    tempData = s.getValue(t);
                    Log.i("password: ", tempData.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        return (T)tempData;
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
    private static ArrayList<Object> tempList;
    public static <T> ArrayList<T> getList(DatabaseReference ref){
        tempList = new ArrayList<Object>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                GenericTypeIndicator<T> t = new GenericTypeIndicator<T>() {};
                for(DataSnapshot d : snapshot.getChildren()){
                    tempList.add(d.getValue(t));
                    Log.i("list: ", tempList.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
        return (ArrayList<T>)tempList;
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
