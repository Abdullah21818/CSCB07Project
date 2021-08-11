package com.example.CSCB07Project;

public class LoginDoctorPresenter {
    private LoginDoctorModel model;
    private LoginDoctorActivity view;

    public LoginDoctorPresenter(LoginDoctorModel model, LoginDoctorActivity view) {
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
