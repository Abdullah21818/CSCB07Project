package com.example.CSCB07Project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_doctor);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        //String password = intent.getStringExtra("password");

        Doctor doctor = FirebaseHelper.getDoctor("Doctor", userId);

        SpannableStringBuilder doctorInfo = makeBold("Username: ", doctor.getUserId());
        doctorInfo.append(makeBold("\nName: ", doctor.getName()));
        doctorInfo.append(makeBold("\nGender: ", doctor.getGender()));
        doctorInfo.append(makeBold("\nSpecializations: ", doctor.getSpecs().toString()));

        TextView userText = findViewById(R.id.userTextView);
        userText.setText(doctorInfo);

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
