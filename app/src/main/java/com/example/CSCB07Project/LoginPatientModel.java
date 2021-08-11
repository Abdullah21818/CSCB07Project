package com.example.CSCB07Project;

import java.util.ArrayList;

import static com.example.CSCB07Project.FirebaseAPI.getAllPasswords;
import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginPatientModel {

    ArrayList<String> usernames;
    ArrayList<String> passwords;

    public LoginPatientModel() {
        usernames = new ArrayList<String>();
        getAllUsername("Patients", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                usernames = data;
            }
        });

        passwords = new ArrayList<String>();
        getAllPasswords("Patients", new Callback<ArrayList<String>>() {
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
