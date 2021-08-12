package com.example.CSCB07Project.PatientFiles;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.MVPInterfaces;

import java.util.ArrayList;

import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginPatientModel implements MVPInterfaces.Model {

    public LoginPatientModel() {
    }

    @Override
    public void usernameNotFound(String username, Callback c){
        FirebaseAPI.getAllUsername("Patients", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                if(data!= null && !data.contains(username))
                    c.onCallback(data);
            }
        });
    }

    @Override
    public void usernameMatchPassword(String username, String password, Callback<Boolean> c){
        FirebaseAPI.getData("Patients/"+username+"/password", (Callback<String>) data -> {
            if(password.equals(data))
                c.onCallback(true);
            else
                c.onCallback(false);
        });
    }
}
