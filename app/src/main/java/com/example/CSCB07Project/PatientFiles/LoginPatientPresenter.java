package com.example.CSCB07Project.PatientFiles;

import com.example.CSCB07Project.MVPInterfaces;

public class LoginPatientPresenter implements MVPInterfaces.Presenter {
    private MVPInterfaces.Model model;
    private MVPInterfaces.View view;

    public LoginPatientPresenter(MVPInterfaces.Model model, MVPInterfaces.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void checkUsernamePassword () {
        if (!model.usernameIsFound(view.getUserId())) {
            view.displayMessage("Invalid Username");
        }

        if (model.passwordIsFound(view.getPassword()) &&
            model.usernameMatchPassword(view.getUserId(), view.getPassword())) {
            view.toDashboard();
        } else {
            view.displayMessage("Wrong Password");
        }
    }
}
