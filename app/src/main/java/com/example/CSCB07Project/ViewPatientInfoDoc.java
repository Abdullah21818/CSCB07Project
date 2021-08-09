package com.example.CSCB07Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import static com.example.CSCB07Project.StyleText.makeBold;

public class ViewPatientInfoDoc extends AppCompatActivity {
    SpannableStringBuilder allinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_info_doc);


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
                    allinfo = StyleText.formatPatientInfo(a,i);
                    SpannableStringBuilder appointInfo = StyleText.formatPatientInfo(a, i);

                  String patientUsername = a.getPatient();
                   FirebaseAPI.getPatient(patientUsername, new Callback<HashMap<String, Object>>() {
                       @Override
                       public void onCallback(HashMap<String, Object> data1) {
                            Patient patient = new Patient(data1);
                            allinfo.append(makeBold("Patient name: ", patient.name) + "\n");
                        }
                    });

//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patients");
//                    ValueEventListener pL = new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                                for(DataSnapshot d:snapshot.getChildren()) {
//                                    if(patientUsername.equals(d.child("userId").getValue().toString())){
//                                        Patient patient = d.getValue(Patient.class);
//                                        Log.i("found user: ", patientUsername);
//                                    appointInfo.append(makeBold("Patient Name: ", patient.name + "\n"));
////                                    appointInfo.append(makeBold("Patient Sex: ", d.child("patientUsername").child("gender").getValue().toString() + "\n"));
////                                    appointInfo.append(makeBold("Birthday: ", d.child("patientUsername").child("birthday").getValue().toString() + "\n"));
////                                    if(patient.visited.size()!=0){
////                                        //appointInfo.append(makeBold("Visited Doctors: ", d.child(patientUsername).child("visited")))
////                                    }
//
//                                    }
//
//                                }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    };
//                    ref.addValueEventListener(pL);

                        TextView appointText = new TextView(ViewPatientInfoDoc.this);
                    appointText.setText(allinfo);

                    View line = new View(ViewPatientInfoDoc.this);
                    line.setBackgroundColor(getResources().getColor(R.color.purple_200));

                    StyleText.formatAppointmentView(params, appointText, line);

                    layout.addView(appointText);
                    layout.addView(line);
                    i++;
                }

                if (doctor.getUpcomingAppointments().size() == 0) {
                    TextView appointText = new TextView(ViewPatientInfoDoc.this);
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