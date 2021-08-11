package com.example.CSCB07Project.DoctorFiles;

import com.example.CSCB07Project.MVPInterfaces;

public class LoginDoctorPresenter implements MVPInterfaces.Presenter {
    private MVPInterfaces.Model model;
    private MVPInterfaces.View view;

    public LoginDoctorPresenter(MVPInterfaces.Model model, MVPInterfaces.View view) {
        this.model = model;
        this.view = view;
    }

    public boolean checkUsernamePassword () {
        if (!model.usernameIsFound(view.getUserId())) {
            view.displayMessage("Invalid Username");
            return false;
        }

        if (model.passwordIsFound(view.getPassword()) &&
                model.usernameMatchPassword(view.getUserId(), view.getPassword())) {
            return true;
        } else {
            view.displayMessage("Wrong Password");
            return false;
        }
    }
}
