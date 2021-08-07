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
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                int i = 1;

                for (Appointment a : doctor.getUpcomingAppointments()) {
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    TextView appointText = new TextView(ViewDoctorAppointments.this);
                    appointText.setText(appointInfo);

                    View line = new View(ViewDoctorAppointments.this);
                    line.setBackgroundColor(getResources().getColor(R.color.purple_200));

                    StyleText.formatAppointmentView(params, appointText, line);

                    layout.addView(appointText);
                    layout.addView(line);
                    i++;
                }

                if (doctor.getUpcomingAppointments().size() == 0) {
                    TextView appointText = new TextView(ViewDoctorAppointments.this);
                    appointText.setText(getResources().getText(R.string.no_appointment));
                    appointText.setTextSize(18);
                    layout.addView(appointText);
                }
            }
        });
    }

    public void backToDashboard(View view) {
        this.finish();
    }
}