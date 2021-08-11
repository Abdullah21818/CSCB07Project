package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginDoctorActivity extends AppCompatActivity {

    private LoginDoctorModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
        model = new LoginDoctorModel();
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
        LoginDoctorPresenter presenter = new LoginDoctorPresenter(model, this);
        presenter.checkUsernamePassword();
    }

    public void toDashboard() {
        AppCompatActivity activity = this;
        Intent intent = new Intent(activity, DoctorDashboard.class);
        intent.putExtra("userId", getUserId());
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }
}