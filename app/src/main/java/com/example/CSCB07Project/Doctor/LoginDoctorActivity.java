package com.example.CSCB07Project.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< Updated upstream:app/src/main/java/com/example/CSCB07Project/LoginDoctorActivity.java
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
=======
import com.example.CSCB07Project.MVPInterfaces;
import com.example.CSCB07Project.PopUp;
import com.example.CSCB07Project.R;

public class LoginDoctorActivity extends AppCompatActivity implements MVPInterfaces.View {

    private MVPInterfaces.Model model;
    private MVPInterfaces.Presenter presenter;
>>>>>>> Stashed changes:app/src/main/java/com/example/CSCB07Project/Doctor/LoginDoctorActivity.java


public class LoginDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
<<<<<<< Updated upstream:app/src/main/java/com/example/CSCB07Project/LoginDoctorActivity.java
    }

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Context context = getApplicationContext();
=======
        model = new LoginDoctorModel();
        presenter = new LoginDoctorPresenter(model, this);
    }

    public String getUserId() {
        return ((EditText) findViewById(R.id.username)).getText().toString();
    }

    public String getPassword() {
        return ((EditText) findViewById(R.id.password)).getText().toString();
    }

    public void displayMessage(String message) {
        Context context = getApplicationContext();
        PopUp.popupMessage(context, message, Toast.LENGTH_SHORT);
    }

    public void signIn(View view){
        presenter.checkUsernamePassword();
    }
>>>>>>> Stashed changes:app/src/main/java/com/example/CSCB07Project/Doctor/LoginDoctorActivity.java

        AppCompatActivity activity = LoginDoctorActivity.this;
        FirebaseAPI.<String>getData("Doctors/" + userId + "/password", new Callback<String>() {
            @Override
            public void onCallback(String data) {
                if(password.equals(data)) {
                    Intent intent = new Intent(activity, DoctorDashboard.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                } else{
                    PopUp.popupMessage(context, "Wrong Password", Toast.LENGTH_SHORT);
                }
            }
        });
        FirebaseAPI.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                if(!data.contains(userId))
                    PopUp.popupMessage(context, "Invalid Username", Toast.LENGTH_SHORT);
            }
        });
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }
}