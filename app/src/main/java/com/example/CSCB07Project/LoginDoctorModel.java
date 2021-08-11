package com.example.CSCB07Project;


import android.telecom.Call;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.CSCB07Project.FirebaseAPI.getAllPasswords;
import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginDoctorModel {

    ArrayList<String> doctorUsernames;
    ArrayList<String> doctorPasswords;

    public void Model(){
        doctorUsernames = new ArrayList<String>();
        getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                Collections.copy(doctorUsernames,data);
            }
        });

        doctorPasswords = new ArrayList<String>();
        getAllPasswords("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                Collections.copy(doctorPasswords, data);
            }
        });
    }

    public boolean docUsernameIsFound(String docUsername){ return doctorUsernames.contains(docUsername); }

    public boolean docPasswordIsFound(String docPassword){ return doctorPasswords.contains(docPassword); }

    public boolean docUsernameMatchPassword(String docUsername, String docPassword){
        return doctorUsernames.indexOf(docUsername)==doctorPasswords.indexOf(docPassword);
    }




}
