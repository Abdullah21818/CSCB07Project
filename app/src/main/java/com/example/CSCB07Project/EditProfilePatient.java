package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfilePatient extends AppCompatActivity {
    String userId;
    String id;
    String pw;
    String name;
    String m;
    String y;
    String d;
    EditText username;
    EditText password;
    EditText newname;
    EditText month;
    EditText year;
    EditText day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra("userId");
        pw = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        m = intent.getStringExtra("month");
        y = intent.getStringExtra("year");
        d = intent.getStringExtra("day");
        setContentView(R.layout.activity_edit_profile_patient);
        username = (EditText)findViewById(R.id.edit_patient_id);
        password = (EditText)findViewById(R.id.edit_patient_password);
        newname = (EditText)findViewById(R.id.newName);
        month = (EditText)findViewById(R.id.edit_patient_newMonth);
        year = (EditText)findViewById(R.id.edit_patient_newYear);
        day = (EditText) findViewById(R.id.edit_patient_newDay);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        username.setText(id);
        password.setText(pw);
        newname.setText(name);
        month.setText(m);
        year.setText(y);
        day.setText(d);

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