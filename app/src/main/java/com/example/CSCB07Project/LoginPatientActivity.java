package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityPatient.class);
        startActivity(intent);
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
        LoginPatientPresenter presenter = new LoginPatientPresenter(new FirebaseAPI(), this);
        presenter.checkUsernamePassword();
    }

    public void toDashboard() {
        AppCompatActivity activity = this;
        Intent intent = new Intent(activity, PatientDashboard.class);
        intent.putExtra("userId", getUserId());
        startActivity(intent);
    }
}