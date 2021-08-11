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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_appointments);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        FirebaseAPI.updateDoctorAppointment(userId);
        updateDisplay(userId);
    }

    private void updateDisplay(String userId) {
        FirebaseAPI.<Doctor>getUpdatingData("Doctors/"+userId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                //Log.i("Doctor info", data.toString());
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
}
