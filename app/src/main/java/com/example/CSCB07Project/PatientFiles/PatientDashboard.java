package com.example.CSCB07Project.PatientFiles;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;
import com.example.CSCB07Project.StyleText;

import java.util.HashMap;

public class PatientDashboard extends AppCompatActivity {
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_patient);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("patientInfo", data.toString());
                Patient patient = new Patient(data);
                SpannableStringBuilder patientInfo = StyleText.formatPatientInfo(patient);

                TextView userText = findViewById(R.id.patientInfo);
                userText.setText(patientInfo);
            }
        });
    }

    public void changeProfile(View view) {
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, EditProfilePatient.class);
        intent2.putExtra("userId", userId);
        startActivity(intent2);
    }

    public void viewAppointment(View view) {
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, ViewPatientAppointments.class);
        intent2.putExtra("userId", userId);
        startActivity(intent2);
    }

    public void bookAppointment(View view){
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, BookAppointment.class);
        intent2.putExtra("patUserId", userId);
        intent2.putExtra("name", intent.getStringExtra("name"));
        intent2.putExtra("gender", intent.getStringExtra("gender"));
        startActivity(intent2);
    }
}
