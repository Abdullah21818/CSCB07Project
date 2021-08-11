package com.example.CSCB07Project.DoctorFiles;

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

public class LoginDoctorActivity extends AppCompatActivity implements MVPInterfaces.View {

    private MVPInterfaces.Model model;
    private MVPInterfaces.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
        model = new LoginDoctorModel();
        presenter = new LoginDoctorPresenter(model, this);
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
    public void signIn(View view){
        presenter.checkUsernamePassword();
    }

    @Override
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