package com.example.CSCB07Project.PatientFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.View;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.DoctorFiles.Doctor;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewRequestedDoctors extends AppCompatActivity {

    private Intent intent;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> userIds = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_doctors);
        intent = getIntent();
        updateListDocs();
    }

    private void updateListDocs() {
        String gender = intent.getStringExtra("gender");
        String specs = intent.getStringExtra("specs");

        FirebaseAPI.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                //loop through all usernames
                for(String username : data){
                    FirebaseAPI.getDoctor(username, new Callback<HashMap<String, Object>>() {
                        @Override
                        public void onCallback(HashMap<String, Object> data) {
                            Doctor doctor = new Doctor(data);
                            if((doctor.getGender().equals(gender) || gender.equals("none")) &&
                                    (doctor.getSpecs().contains(specs) || specs.equals("none"))){
                                names.add("Dr. "+doctor.getName());
                                userIds.add(username);
                                updateList();
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateList() {
        ListView lv = findViewById(R.id.listDocs);
        String[] docInfo = names.toArray(new String[0]);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, docInfo);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newActivity = new Intent(ViewRequestedDoctors.this,
                                    BookDoctorTimeSlot.class);
                newActivity.putExtra("docUserId", userIds.get(i));
                newActivity.putExtra("patUserId", intent.getStringExtra("patUserId"));
                startActivity(newActivity);
                finish();
            }
        });
    }
}