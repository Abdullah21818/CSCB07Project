package com.example.CSCB07Project;

public class LoginDoctorPresenter {
    private LoginDoctorModel model;
    private LoginDoctorActivity view;

    public LoginDoctorPresenter(LoginDoctorModel model, LoginDoctorActivity view) {
        this.model = model;
        this.view = view;
    }

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
