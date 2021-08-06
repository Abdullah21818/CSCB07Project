package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class ViewPatientAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_appointments);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("Patient info",data.toString());
                Patient patient = new Patient(data);

                LinearLayout layout = findViewById(R.id.appointLayout);
                int i = 1;

                for (Appointment a : patient.getUpcomingAppointments()) {
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    TextView appointText = new TextView(ViewPatientAppointments.this);
                    appointText.setText(appointInfo);
                    layout.addView(appointText);
                    i++;
                }
            }
        });
    }

    public void backToDashboard(View view) {/*
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, PatientDashboard.class);
        intent2.putExtra("userId", intent.getStringExtra("userId"));
        startActivity(intent2);*/
        this.finish();
    }
}