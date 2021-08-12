package com.example.CSCB07Project.PatientFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CSCB07Project.MVPInterfaces;
import com.example.CSCB07Project.PopUp;
import com.example.CSCB07Project.R;

public class LoginPatientActivity extends AppCompatActivity implements MVPInterfaces.View {

    private MVPInterfaces.Model model;
    private MVPInterfaces.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        model = new LoginPatientModel();
        presenter = new LoginPatientPresenter(model, this);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityPatient.class);
        startActivity(intent);
    }

    @Override
    public String getUserId() {
        return ((EditText) findViewById(R.id.username)).getText().toString();
    }

    @Override
    public String getPassword() {
        return ((EditText) findViewById(R.id.password)).getText().toString();
    }

    @Override
    public void displayMessage(String message) {
        Context context = getApplicationContext();
        PopUp.popupMessage(context, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void signIn(View view) {
        presenter.checkUsernamePassword(data -> toDashboard());
    }

    @Override
    public boolean toDashboard() {
        AppCompatActivity activity = this;
        Intent intent = new Intent(activity, PatientDashboard.class);
        intent.putExtra("userId", getUserId());
        startActivity(intent);
        return true;
    }
}