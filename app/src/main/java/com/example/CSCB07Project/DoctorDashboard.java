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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class DoctorDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

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
        Date date = new Date(System.currentTimeMillis());
        com.example.CSCB07Project.Date time = new com.example.CSCB07Project.Date(date);
        Bundle map = getIntent().getExtras();
        String userId = map.getString("userId");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors/" +  userId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Doctor doctor =  snapshot.getValue(Doctor.class);
                if (doctor.upcomingAppoint == null){
                    return;
                }
                for (Appointment apt : doctor.upcomingAppoint){
                    if (apt.end.before(time)){
                        doctor.upcomingAppoint.remove(apt);
                        doctor.visited.add(apt.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        ref.addValueEventListener(listener);
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, ViewDoctorAppointments.class);
        intent2.putExtra("userId", intent.getStringExtra("userId"));
        startActivity(intent2);
    }
}
