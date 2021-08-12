package com.example.CSCB07Project.DoctorFiles;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CSCB07Project.Appointment;
import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;
import com.example.CSCB07Project.StyleText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorDashboard extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        Log.i("patient username", userId);
        FirebaseAPI.getDoctor(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("doctorInfo", data.toString());
                Doctor doctor = new Doctor(data);
                SpannableStringBuilder doctorInfo = StyleText.makeBold("Username: ",
                        doctor.getUserId());
                doctorInfo.append(StyleText.makeBold("\nName: ", doctor.getName()));
                doctorInfo.append(StyleText.makeBold("\nGender: ", doctor.getGender()));
                doctorInfo.append(StyleText.makeBold("\nSpecializations: ",
                        doctor.getSpecs().toString()));

                TextView userText = findViewById(R.id.doctorInfo);
                userText.setText(doctorInfo);
            }
        });
    }

    public void changeProfile(View view) {
        //Intent intent2 = new Intent(this, EditProfilePatient.class);
        //intent2.putExtra("userId", userId);
        //startActivity(intent2);
    }

    public void viewAppointment(View view) {
        Intent intent2 = new Intent(this, ViewDoctorAppointments.class);
        intent2.putExtra("userId", userId);
        startActivity(intent2);
    }

    public void viewPatients(View view) {
        Intent intent2 = new Intent(this, ViewPatInfo.class);
        intent2.putExtra("userId", userId);
        startActivity(intent2);
    }

    public void deleteAccount(View view) {
        FirebaseAPI.getDoctor(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("doctorInfo2", data.toString());
                Doctor doctor = new Doctor(data);
                for (Appointment a : doctor.getUpcomingAppointments()) {
                    FirebaseAPI.deleteAppointment(a);
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Doctors");
                ref.child(userId).removeValue();
                DoctorDashboard.this.finish();
            }
        });
    }
}
