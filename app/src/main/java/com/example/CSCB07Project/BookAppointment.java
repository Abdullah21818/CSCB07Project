package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class BookAppointment extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    Intent intent;
    ArrayList<String> specs = new ArrayList<String>();
    ArrayList<String> everything = new ArrayList<String>();
    String patientNeedSpec;
    String patientNeedGender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        intent = getIntent();
        specs.add("Please choose a specialization");

        DatabaseReference ref = FirebaseDatabase
                                .getInstance("https://cscb07-f18e5-default-rtdb.firebaseio.com/")
                                .getReference("Doctors");

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
        ref.addValueEventListener(dL);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        parent.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
