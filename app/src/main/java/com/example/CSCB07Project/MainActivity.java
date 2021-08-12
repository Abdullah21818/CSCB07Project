package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chooseUserType(View view){
        RadioButton userTypePatient = (RadioButton) findViewById(R.id.user_Patient);
        RadioButton userTypeDoctor = (RadioButton) findViewById(R.id.user_Doctor);


        if (userTypePatient.isChecked()) {
            Toast.makeText(MainActivity.this, "Welcome patience", Toast.LENGTH_SHORT).show();
            patientLogin(view);
        }
        else if (userTypeDoctor.isChecked()) {
            //go to login page for doctor
            Toast.makeText(MainActivity.this, "Welcome doctor", Toast.LENGTH_SHORT).show();
            doctorLogin(view);
        }
    }

    public void patientLogin(View view) {
        Intent intent = new Intent(this, LoginPatientActivity.class);
        startActivity(intent);
    }

    public void doctorLogin(View view) {
        Intent intent = new Intent(this, LoginDoctorActivity.class);
        startActivity(intent);
    }
}