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

import java.util.ArrayList;
import java.util.List;

public class FirebaseAPI {
    /**
     * This function gets the value in the path of the reference
     * @param ref - the database reference
     * @return the value in the path of ref
     */
    public static <DataType> void getData (DatabaseReference ref, Callback c){
        ValueEventListener l = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    GenericTypeIndicator<DataType> t = new GenericTypeIndicator<DataType>() {};
                    c.onCallback(snapshot.getValue(t));
                } catch(Exception e){
                    Log.d("Error", "Data not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        };
        ref.addValueEventListener(l);
    }

    /**
     * This function gets the value in the path of the reference,
     * used when not certain if the path exists or not
     * @param ref - the database reference
     * @param path - the path after the ref
     * @return the value in the path of ref, null if path does not exist
     */
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
    }
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

    public static void updateList(String className, String userId, String listName, List list){
        String databaseName = className + "s";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(databaseName).child(userId).child(listName).setValue(list);
    }
}