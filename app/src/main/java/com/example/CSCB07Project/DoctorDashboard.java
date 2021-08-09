package com.example.CSCB07Project;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

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
}
