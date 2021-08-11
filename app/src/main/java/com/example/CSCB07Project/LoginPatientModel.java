package com.example.CSCB07Project;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.CSCB07Project.FirebaseAPI.getAllPasswords;
import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginPatientModel {

    ArrayList<String> patientUsernames;
    ArrayList<String> patientPasswords;

    public void Model(){
        patientUsernames = new ArrayList<String>();
        getAllUsername("Patients", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                Collections.copy(patientUsernames,data);
            }
        });

        patientPasswords = new ArrayList<String>();
        getAllPasswords("Patients", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                Collections.copy(patientPasswords, data);
            }
        });
    }

    public boolean docUsernameIsFound(String patUsername){ return patientUsernames.contains(patUsername); }

    public boolean docPasswordIsFound(String patPassword){ return patientPasswords.contains(patPassword); }

    public boolean docUsernameMatchPassword(String docUsername, String docPassword){
        return patientUsernames.indexOf(docUsername)==patientPasswords.indexOf(docPassword);
    }
}
