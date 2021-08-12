package com.example.CSCB07Project.DoctorFiles;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.MVPInterfaces;

import java.util.ArrayList;

public class LoginDoctorPresenter implements MVPInterfaces.Presenter {
    private MVPInterfaces.Model model;
    private MVPInterfaces.View view;

    public LoginDoctorPresenter(MVPInterfaces.Model model, MVPInterfaces.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void checkUsernamePassword (Callback c) {
        model.usernameNotFound(view.getUserId(), new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                view.displayMessage("Invalid Username");
            }
        });

        model.usernameMatchPassword(view.getUserId(), view.getPassword(), new Callback<Boolean>() {
            @Override
            public void onCallback(Boolean data) {
                if(data)
                    c.onCallback(true);
                else
                    view.displayMessage("Wrong Password");
            }
        });
    }
}
