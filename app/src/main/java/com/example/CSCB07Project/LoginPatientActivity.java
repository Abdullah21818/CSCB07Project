package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Context context = getApplicationContext();

        AppCompatActivity activity = this;
        FirebaseAPI.<String>getData("Patients/" + userId + "/password", new Callback<String>() {
            @Override
            public void onCallback(String data) {
                if(password.equals(data)) {
                    Intent intent = new Intent(activity, PatientDashboard.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                } else{
                    PopUp.popupMessage(context, "Wrong Password", Toast.LENGTH_SHORT);
                }
            }
        });
        FirebaseAPI.getAllUsername("Patients", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                if(!data.contains(userId))
                    PopUp.popupMessage(context, "Invalid Username", Toast.LENGTH_SHORT);
            }
        });
    }
}