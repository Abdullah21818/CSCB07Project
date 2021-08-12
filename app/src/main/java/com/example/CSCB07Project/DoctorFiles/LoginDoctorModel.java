package com.example.CSCB07Project.DoctorFiles;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.MVPInterfaces;

import java.util.ArrayList;

import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginDoctorModel implements MVPInterfaces.Model {

    public LoginDoctorModel() {
    }

    @Override
    public void usernameNotFound(String username, Callback c){
        FirebaseAPI.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                if(data!= null && !data.contains(username))
                    c.onCallback(data);
            }
        });
    }

    @Override
    public void usernameMatchPassword(String username, String password, Callback<Boolean> c){
        FirebaseAPI.getData("Doctors/"+username+"/password", (Callback<String>) data -> {
            if(password.equals(data))
                c.onCallback(true);
            else
                c.onCallback(false);
        });
    }
}
