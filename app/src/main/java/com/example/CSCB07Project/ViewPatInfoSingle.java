package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class ViewPatInfoSingle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pat_info_single);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("patId");
        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("Patient info",data.toString());
                Patient patient = new Patient(data);
                SpannableStringBuilder patientInfo = StyleText.formatPatientInfo(patient);

                TextView userText = findViewById(R.id.patientInf2);
                userText.setText(patientInfo);
            }
        });

    }

    public void backToViewPatInfo(View view) {
        this.finish();
    }
}