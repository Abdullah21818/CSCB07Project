package com.example.CSCB07Project;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public interface MVPInterfaces {
    public interface Model{
        public void usernameNotFound(String username, Callback c);

        public void usernameMatchPassword(String username, String password, Callback<Boolean> c);
    }

    public interface Presenter{
        public void checkUsernamePassword (Callback c);
    }

    public interface View{
        public String getUserId();

        public String getPassword();

        public void displayMessage(String message);

        public void signIn(android.view.View view);

        public boolean toDashboard();
    }
}
