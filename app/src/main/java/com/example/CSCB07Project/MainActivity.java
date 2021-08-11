package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.CSCB07Project.DoctorFiles.LoginDoctorActivity;
import com.example.CSCB07Project.PatientFiles.LoginPatientActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chooseUserType(View view){
        RadioButton userTypePatient = findViewById(R.id.user_Patient);
        RadioButton userTypeDoctor = findViewById(R.id.user_Doctor);

        if (userTypePatient.isChecked()) {
            patientLogin(view);
        }
        else if (userTypeDoctor.isChecked()) {
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