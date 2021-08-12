package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginPatientActivity extends AppCompatActivity {
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityPatient.class);
        startActivity(intent);
    }

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                patient = new Patient(data);
            }
        });
        AppCompatActivity activity = this;
        FirebaseAPI.<String>getData("Patients/" + userId + "/password", new Callback<String>() {
            @Override
            public void onCallback(String data) {
                if(password.equals(data)) {
                    Intent intent = new Intent(activity, PatientDashboard.class);
                    intent.putExtra("userId",patient.getUserId());
                    intent.putExtra("name", patient.getName());
                    intent.putExtra("password", patient.getPassword());
                    intent.putExtra("month", patient.getBirthday().getMonth());
                    intent.putExtra("year", patient.getBirthday().getYear());
                    intent.putExtra("day", patient.getBirthday().getDay());
                    startActivity(intent);
                }
            }
        });
    }
}

