package com.example.CSCB07Project;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
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
                SpannableStringBuilder doctorInfo = makeBold("Username: ", doctor.getUserId());
                doctorInfo.append(makeBold("\nName: ", doctor.getName()));
                doctorInfo.append(makeBold("\nGender: ", doctor.getGender()));
                doctorInfo.append(makeBold("\nSpecializations: ", doctor.getSpecs().toString()));

                TextView userText = findViewById(R.id.userTextView);
                userText.setText(doctorInfo);
            }
        });
        // Remember to get upcomingAppoint info and put it in the TextView in appointScroll
        // Right now reading appointment info from Firebase doesn't work?
        // Might need user info as well -- I haven't yet found a way to make dynamic additions of
        // components
    }

    private SpannableStringBuilder makeBold(String boldText, String text) {
        SpannableStringBuilder info = new SpannableStringBuilder(boldText + text);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
        info.setSpan(bold, 0, boldText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return info;
    }
}
