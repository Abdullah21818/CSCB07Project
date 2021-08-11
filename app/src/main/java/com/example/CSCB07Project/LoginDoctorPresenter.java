package com.example.CSCB07Project;

import java.util.ArrayList;

public class LoginDoctorPresenter {
    private LoginDoctorModel model;
    private LoginDoctorActivity view;

    public LoginDoctorPresenter(LoginDoctorModel model, LoginDoctorActivity view) {
        this.model = model;
        this.view = view;
    }

    public void checkUsernamePassword() {
//        model.<String>getData("Doctors/" + view.getUserId() + "/password",
//                new Callback<java.lang.String>() {
//                    @Override
//                    public void onCallback(java.lang.String data) {
//                        if (view.getPassword().equals(data)) {
//                            view.toDashboard();
//                        } else {
//                            view.displayMessage("Wrong Password");
//                        }
//                    }
//                });
//
//        model.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
//            @Override
//            public void onCallback(ArrayList<String> data) {
//                if (!data.contains(view.getUserId()))
//                    view.displayMessage("Invalid Username");
//            }
//        });
    }
}
