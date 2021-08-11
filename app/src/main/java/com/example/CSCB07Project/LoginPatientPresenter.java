package com.example.CSCB07Project;

public class LoginPatientPresenter {
    private LoginPatientModel model;
    private LoginPatientActivity view;

    public LoginPatientPresenter(LoginPatientModel model, LoginPatientActivity view) {
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
