package com.example.CSCB07Project;

import android.view.View;

public interface MVPInterfaces {
    public interface Model{
        public boolean usernameIsFound(String username);

        public boolean passwordIsFound(String password);

        public boolean usernameMatchPassword(String username, String password);
    }

    public interface Presenter{
        public boolean checkUsernamePassword ();
    }

    public interface View{
        public String getUserId();

        public String getPassword();

        public void displayMessage(String message);

        public void signIn(android.view.View view);

        public void toDashboard();
    }
}
