package com.example.CSCB07Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

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

        FirebaseAPI.getDoctor(docId, new Callback<HashMap<String, Object>>() {
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
                    SpannableStringBuilder appointInfo = StyleText.formatAppointment(a, i);
                    appointInfoAll.add(appointInfo);
//                    TextView appointText = new TextView(ViewPatInfo.this);
//                    appointText.setText(appointInfo);
//
//                    View line = new View(ViewPatInfo.this);
//                    line.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//                    StyleText.formatAppointmentView(params, appointText, line);
//
//                    layout.addView(appointText);
//                    layout.addView(line);
                    i++;
                }


                SpannableStringBuilder [] docInfoAppoint = new SpannableStringBuilder[appointInfoAll.size()];
                for(int j=0;j<appointInfoAll.size();j++){
                    docInfoAppoint[j]=appointInfoAll.get(j);
                }

                ListView spin = (ListView) findViewById(R.id.patInfoView);
                ArrayAdapter<SpannableStringBuilder> adapter = new ArrayAdapter<SpannableStringBuilder>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, docInfoAppoint);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);

                spin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayList<Appointment> a = doctor.getUpcomingAppointments();
                        Appointment target = a.get(i);
                        Intent intent2 = new Intent(ViewPatInfo.this, ViewPatInfoSingle.class);
                        intent2.putExtra("docId", docId);
                        intent2.putExtra("patId",target.getPatient());
                        startActivity(intent2);
                    }
                });

                if (doctor.getUpcomingAppointments().size() == 0) {
                    TextView appointText = new TextView(ViewPatInfo.this);
                    appointText.setText(getResources().getText(R.string.no_appointment));
                    appointText.setTextSize(18);
                    layout.addView(appointText);
                }
            }
        });

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors");
//        ValueEventListener dL = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                for(DataSnapshot d:snapshot.getChildren()){
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        };
//
//        ref.addValueEventListener(dL);
    }

}