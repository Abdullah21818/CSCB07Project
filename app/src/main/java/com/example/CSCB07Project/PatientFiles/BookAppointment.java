package com.example.CSCB07Project.PatientFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import android.view.View;

import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.R;

import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> specs = new ArrayList<String>();
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        specs.add("No specialization required");
        spin = (Spinner) findViewById(R.id.specChoose);
        updateSpecializations();
    }

    private void updateSpecializations() {
        FirebaseAPI.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
            @Override
            public void onCallback(ArrayList<String> data) {
                //loop through all usernames
                for(String username : data){
                    FirebaseAPI.<ArrayList<String>>getData("Doctors/"+username+"/specs", new Callback<ArrayList<String>>() {
                        @Override
                        public void onCallback(ArrayList<String> data) {
                            //loop through all specializations of doctor and add them to specs
                            if(data != null) {
                                for (String specialization : data) {
                                    if (!specs.contains(specialization)) {
                                        specs.add(specialization);
                                    }
                                }
                                updateSpecChoose();
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateSpecChoose(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, specs.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void searchDoctor(View view){
        RadioButton docGenderMale = (RadioButton) findViewById(R.id.male);
        RadioButton docGenderFemale = (RadioButton) findViewById(R.id.female);

        //Getting the selected gender
        String patientNeedGender = "none";
        if(docGenderMale.isChecked())
            patientNeedGender = "Male";
        else if(docGenderFemale.isChecked())
            patientNeedGender = "Female";

        //Getting the selected specialization
        String patientNeedSpec;
        patientNeedSpec = spin.getSelectedItem().toString();
        if(patientNeedSpec == "No specialization required"){
            patientNeedSpec = "none";
        }

        Intent intent = getIntent();
        Intent newActivity = new Intent(BookAppointment.this, ViewRequestedDoctors.class);
        newActivity.putExtra("gender", patientNeedGender);
        newActivity.putExtra("specs", patientNeedSpec);
        newActivity.putExtra("patUserId", intent.getStringExtra("patUserId"));
        startActivity(newActivity);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        parent.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
