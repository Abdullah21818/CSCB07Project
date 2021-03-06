package com.example.CSCB07Project.PatientFiles;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;

import java.util.HashMap;

public class EditProfilePatient extends AppCompatActivity {
    private String userId;
    private EditText username;
    private EditText password;
    private EditText newname;
    private EditText month;
    private EditText year;
    private EditText day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        setContentView(R.layout.activity_edit_profile_patient);
        username = (EditText)findViewById(R.id.edit_patient_id);
        password = (EditText)findViewById(R.id.edit_patient_password);
        newname = (EditText)findViewById(R.id.newName);
        month = (EditText)findViewById(R.id.edit_patient_newMonth);
        year = (EditText)findViewById(R.id.edit_patient_newYear);
        day = (EditText) findViewById(R.id.edit_patient_newDay);
        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Patient patient = new Patient(data);
                username.setText(patient.getUserId());
                password.setText(patient.getPassword());
                newname.setText(patient.getName());
                month.setText(patient.getBirthday().getMonth());
                year.setText(patient.getBirthday().getYear());
                day.setText(patient.getBirthday().getDay());
            }
        });
    }
}