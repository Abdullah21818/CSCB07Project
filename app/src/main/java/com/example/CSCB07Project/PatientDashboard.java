package com.example.CSCB07Project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class PatientDashboard extends AppCompatActivity {
    String userId;
    String password;
    String newname;
    String month;
    String year;
    String day;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_patient);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        password = intent.getStringExtra("password");
        newname = intent.getStringExtra("name");
        month = intent.getStringExtra("month");
        year = intent.getStringExtra("year");
        day = intent.getStringExtra("day");

        FirebaseAPI.getPatient(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("Patient info",data.toString());
                Patient patient = new Patient(data);
//                password = patient.getPassword();
//                newname = patient.getName();
//                month = patient.getBirthday().getMonth() + "";
//                year = patient.getBirthday().getYear() + "";
//                day = patient.getBirthday().getDay() + "";
                SpannableStringBuilder patientInfo = StyleText.makeBold("Username: ",
                                                    patient.getUserId());
                patientInfo.append(StyleText.makeBold("\nName: ", patient.getName()));
                patientInfo.append(StyleText.makeBold("\nGender: ", patient.getGender()));

                TextView userText = findViewById(R.id.patientInfo);
                userText.setText(patientInfo);
            }
        });
    }

    public void viewAppointment(View view) {
        Date date = new Date(System.currentTimeMillis());
        com.example.CSCB07Project.Date time = new com.example.CSCB07Project.Date(date);
        String userId = getIntent().getExtras().getString("userId");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patients/" + userId);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Patient patient = snapshot.getValue(Patient.class);
                if (patient.upcomingAppoint == null){
                    return;
                }
                for (Appointment apt : patient.upcomingAppoint){
                    if (apt.end.before(time)){
                        patient.upcomingAppoint.remove(apt);
                        patient.visited.add(apt.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, ViewPatientAppointments.class);
        intent2.putExtra("userId", intent.getStringExtra("userId"));
        startActivity(intent2);
    }

    public void bookAppointment(View view){
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, BookAppointment.class);
        intent2.putExtra("userId", intent.getStringExtra("userId"));
        intent2.putExtra("name", intent.getStringExtra("name"));
        intent2.putExtra("gender", intent.getStringExtra("gender"));
        startActivity(intent2);

    }

    public void editSelfProfile(View view){
        Intent intent = new Intent(PatientDashboard.this, EditProfilePatient.class);
        intent.putExtra("userId",userId);
        intent.putExtra("name", newname);
        intent.putExtra("password", password);
        intent.putExtra("month", month);
        intent.putExtra("year", year);
        intent.putExtra("day", day);
        startActivity(intent);
    }


}
