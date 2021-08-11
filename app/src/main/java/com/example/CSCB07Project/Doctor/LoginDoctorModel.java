package com.example.CSCB07Project.Doctor;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.MVPInterfaces;

import java.util.ArrayList;

import static com.example.CSCB07Project.FirebaseAPI.getAllPasswords;
import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginDoctorModel implements MVPInterfaces.Model {

    ArrayList<String> usernames;
    ArrayList<String> passwords;

    public LoginDoctorModel() {
        usernames = new ArrayList<String>();
        getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                usernames = data;
            }
        });

        passwords = new ArrayList<String>();
        getAllPasswords("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                passwords = data;
            }
        });
    }

    public boolean usernameIsFound(String username){ return usernames.contains(username); }

    public boolean passwordIsFound(String password){ return passwords.contains(password); }

    public boolean usernameMatchPassword(String username, String password){
        return usernames.indexOf(username) == passwords.indexOf(password);
    }
}
