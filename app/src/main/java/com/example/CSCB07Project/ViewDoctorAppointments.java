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

public class ViewDoctorAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_appointments);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        FirebaseAPI.getDoctor(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("Doctor info", data.toString());
                Doctor doctor = new Doctor(data);

                LinearLayout layout = findViewById(R.id.appointLayout);
                int i = 1;

                for (Appointment a : doctor.getUpcomingAppoint()) {
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    TextView appointText = new TextView(ViewDoctorAppointments.this);
                    appointText.setText(appointInfo);
                    layout.addView(appointText);
                    i++;
                }
            }
        });
    }

    public void backToDashboard(View view) {
        /*
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, DoctorDashboard.class);
        intent2.putExtra("userId", intent.getStringExtra("userId"));
        startActivity(intent2);*/
        this.finish();
    }
}