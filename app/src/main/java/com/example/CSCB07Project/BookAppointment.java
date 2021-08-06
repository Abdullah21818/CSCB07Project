package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

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
        /*
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors");

        ValueEventListener dL = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot d:snapshot.getChildren()){
                    //String username = d.child("userId").getValue().toString();
                    //Log.i("Username: ", username);

                    StringBuffer sb = new StringBuffer();
                    //casting to arraylist
                    for(String s:(ArrayList<String>)d.child("specs").getValue()){
                        sb.append(s);
                        sb.append(" ");
                    }
                    String special = sb.toString();
                    specs.add(special);

                    String s = "Dr. " + d.child("name").getValue().toString()
                                + ", Specializes in: " + special;

                    everything.add(s);
                }

                String [] specializations = specs.toArray(new String[0]);
                String [] docInfo = everything.toArray(new String[0]);

                Spinner spin = (Spinner) findViewById(R.id.specChoose);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_item, specializations);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);

//               Spinner spinner = (Spinner) findViewById(R.id.specChoose);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i,
                                               long l) {
                        patientNeedSpec = (String) spin.getItemAtPosition(i);
                        RadioButton docGenderMale = (RadioButton) findViewById(R.id.male);
                        RadioButton docGenderFemale = (RadioButton) findViewById(R.id.female);
                        if(docGenderMale.isChecked()){
                            patientNeedGender = "male";
                            Intent intent2 = new Intent(BookAppointment.this,
                                            ViewRequestedDoctors.class);
                            intent2.putExtra("gender", patientNeedGender);
                            intent2.putExtra("specs", patientNeedSpec);
                            startActivity(intent2);
                        }
                        else if(docGenderFemale.isChecked()){

                            patientNeedGender = "female";
                            Intent intent2 = new Intent(BookAppointment.this,
                                            ViewRequestedDoctors.class);
                            intent2.putExtra("gender", patientNeedGender);
                            intent2.putExtra("specs", patientNeedSpec);
                            startActivity(intent2);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


//                TextView t1 = findViewById(R.id.allSpecs);
//                t1.setText(allUserId);
//                ArrayAdapter<CharSequence> adapter =  ArrayAdapter
//                                                      .createFromResource(getApplicationContext(),
//                                                      stuff,
//                                                      android.R.layout.simple_dropdown_item_1line);
//                Spinner lv = (Spinner) findViewById(R.id.specChoose);
//                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        };
        ref.addValueEventListener(dL);*/
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

        String patientNeedGender = "none";
        //Getting the selected gender
        if(docGenderMale.isChecked())
            patientNeedGender = "male";
        else if(docGenderFemale.isChecked())
            patientNeedGender = "female";
        Intent newActivity = new Intent(BookAppointment.this, ViewRequestedDoctors.class);

        //Getting the selected specialization
        String patientNeedSpec;
        patientNeedSpec = spin.getSelectedItem().toString();
        if(patientNeedSpec == "No specialization required"){
            patientNeedSpec = "none";
        }

        newActivity.putExtra("gender", patientNeedGender);
        newActivity.putExtra("specs", patientNeedSpec);
        startActivity(newActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        parent.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
