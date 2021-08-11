package com.example.CSCB07Project;

import java.util.ArrayList;

public class LoginPatientPresenter {
    private LoginPatientModel model;
    private LoginPatientActivity view;

    public LoginPatientPresenter(LoginPatientModel model, LoginPatientActivity view) {
        this.model = model;
        this.view = view;
    }

    public void checkUsernamePassword () {
//        model.<String>getData("Patients/" + view.getUserId() + "/password",
//                                    new Callback<java.lang.String>() {
//            @Override
//            public void onCallback(java.lang.String data) {
//                if (view.getPassword().equals(data)) {
//                    view.toDashboard();
//                } else {
//                    view.displayMessage("Wrong Password");
//                }
//            }
//        });
//
//        model.getAllUsername("Patients", new Callback<ArrayList<String>>() {
//            @Override
//            public void onCallback(ArrayList<String> data) {
//                if(!data.contains(view.getUserId()))
//                    view.displayMessage("Invalid Username");
//            }
//        });
    }
}
