package com.example.cscb07_project;

import com.example.cscb07_project.data.model.User;

import java.util.ArrayList;

public class Doctor extends User {
    private ArrayList <String> specs;



    public Doctor(String userId, String name) {
        super(userId, name);
    }
}
