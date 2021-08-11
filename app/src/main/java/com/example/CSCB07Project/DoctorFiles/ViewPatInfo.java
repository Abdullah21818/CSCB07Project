package com.example.CSCB07Project.DoctorFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.CSCB07Project.Appointment;
import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;
import com.example.CSCB07Project.StyleText;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPatInfo extends AppCompatActivity {

    private ArrayList <SpannableStringBuilder> appointInfoAll = new ArrayList<SpannableStringBuilder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pat_info);

        Intent intent = getIntent();
        String docId = intent.getStringExtra("userId");

        FirebaseAPI.updateDoctorAppointment(docId);
        updateDisplay(docId);
    }

    private void updateDisplay(String docId) {
        FirebaseAPI.getUpdatingData("Doctors/"+docId, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);

                int i = 1;
                for (Appointment a : doctor.getUpcomingAppointments()) {
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    appointInfoAll.add(appointInfo);
                    i++;
                }

                if (doctor.getUpcomingAppointments().size() == 0) {
                    String n = getResources().getText(R.string.no_patient).toString();
                    SpannableStringBuilder notice = new SpannableStringBuilder(n);
                    StyleText.formatNotice(notice, n.length());
                    appointInfoAll.add(notice);
                }

                ListView spin = (ListView) findViewById(R.id.patInfoView);
                ArrayAdapter<SpannableStringBuilder> adapter =
                        new ArrayAdapter<SpannableStringBuilder>(getApplicationContext(),
                                R.layout.list_item_view, R.id.listItemTextView, appointInfoAll);
                spin.setAdapter(adapter);

                spin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayList<Appointment> a = doctor.getUpcomingAppointments();
                        Appointment target = a.get(i);
                        Intent intent2 = new Intent(ViewPatInfo.this,
                                ViewPatInfoSingle.class);
                        intent2.putExtra("docId", docId);
                        intent2.putExtra("patId",target.getPatient());
                        startActivity(intent2);
                    }
                });
            }
        });
    }

    public void backToDashboard(View view) {
        this.finish();
    }
}