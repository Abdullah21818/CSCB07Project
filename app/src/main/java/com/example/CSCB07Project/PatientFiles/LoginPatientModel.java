package com.example.CSCB07Project.PatientFiles;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.MVPInterfaces;

import java.util.ArrayList;

import static com.example.CSCB07Project.FirebaseAPI.getAllPasswords;
import static com.example.CSCB07Project.FirebaseAPI.getAllUsername;

public class LoginPatientModel implements MVPInterfaces.Model {

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

    @Override
    public boolean usernameIsFound(String username){ return usernames.contains(username); }

    @Override
    public boolean passwordIsFound(String password){ return passwords.contains(password); }

    @Override
    public boolean usernameMatchPassword(String username, String password){
        int userIndex = usernames.indexOf(username);
        if(userIndex != -1 && userIndex<passwords.size())
            return password.equals(passwords.get(userIndex));
        return false;
    }
}
