package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewDoctorAppointments extends AppCompatActivity {
    private String docUsername;
    private String patUsername;
    //private String patUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_appointments);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        docUsername = intent.getStringExtra("userId");
        deleteAppointment();

        FirebaseAPI.getDoctor(userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Log.i("Doctor info", data.toString());
                Doctor doctor = new Doctor(data);

                ListView list = findViewById(R.id.appointList);
                ArrayList<SpannableStringBuilder> allAppointInfo = new ArrayList<SpannableStringBuilder>();
                int i = 1;

                for (Appointment a : doctor.getUpcomingAppointments()) {
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    allAppointInfo.add(appointInfo);
                    i++;
                }

                ArrayAdapter<SpannableStringBuilder> adapter =
                        new ArrayAdapter<SpannableStringBuilder>(getApplicationContext(),
                                R.layout.list_item_view, R.id.listItemTextView, allAppointInfo);
                list.setAdapter(adapter);

                if (doctor.getUpcomingAppointments().size() == 0) {
                    String n = getResources().getText(R.string.no_appointment).toString();
                    SpannableStringBuilder notice = new SpannableStringBuilder(n);
                    StyleText.formatNotice(notice, n.length());
                    allAppointInfo.add(notice);
                }
            }
        });
    }

    public void backToDashboard(View view) {
        this.finish();
    }

    public void deleteAppointment() {
        FirebaseAPI.getDoctor(docUsername, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);

                //get current month, day, and year
                double curMonth = (double) (Calendar.getInstance().get(Calendar.MONTH) + 1.0) / 12.0;
                double curDay = (double) (Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) / 365.0;
                double curHour = (double) (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) / 8760.0;
                double curMin = (double) (Calendar.getInstance().get(Calendar.MINUTE)) / 525600.0;

                double curDate = (double) Calendar.getInstance().get(Calendar.YEAR) + curMonth + curDay + curHour + curMin;


                for (Appointment a : doctor.getUpcomingAppointments()) {
                    double endYear = a.getEnd().getYear();
                    double endMonth = (double) (a.getEnd().getMonth()) / 12.0;
                    double endDay = (double) (a.getEnd().getDay()) / 365.0;
                    double endHour = (double) (a.getEnd().getHour()) / 8760.0;
                    double endMin = (double) (a.getEnd().getMinute()) / 525600.0;
                    double endDate = endYear + endMonth + endDay + endHour + endMin;
                    if (curDate >= endDate) {
                        patUsername = a.getPatient();
                        doctor.addVisited(patUsername);

                        FirebaseAPI.getPatient(patUsername, new Callback<HashMap<String, Object>>() {
                            @Override
                            public void onCallback(HashMap<String, Object> data1) {
                                Patient patient = new Patient(data1);
                                patient.addVisited(docUsername);
                                patient.removeAppointment(a);
                                finish();
                            }
                        });
                        doctor.removeAppointment(a);
                    }
                }

            }
//
//                Appointment a = new Appointment(docUsername, patUsername, new Date(month, day, year
//                        , SelectedHour,0), new Date(month, day, year, SelectedHour + 1,0));
//                //Log.i("doctorInfo",doctor.getUpcomingAppointments().toString());
//                //Log.i("Appointment", a.getDoctor() + " " + a.getPatient() + " " + a.getStart().toString() + " " + a.getEnd().toString());
//                doctor.addAppointment(a);
//                FirebaseAPI.getPatient(patUsername, new Callback<HashMap<String, Object>>() {
//                    @Override
//                    public void onCallback(HashMap<String, Object> data) {
//                        Patient patient = new Patient(data);
//                        patient.addAppointment(a);
//                        finish();
//                    }
//                });
        });
    }
}
