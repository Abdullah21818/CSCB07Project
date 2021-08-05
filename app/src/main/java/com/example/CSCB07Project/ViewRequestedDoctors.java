package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

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

public class ViewRequestedDoctors extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> everything = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_doctors);

        Intent intent = getIntent();
        String docGen = intent.getStringExtra("gender").toLowerCase();
        String specialization = intent.getStringExtra("specs");

        DatabaseReference ref = FirebaseDatabase
                                .getInstance("https://cscb07-f18e5-default-rtdb.firebaseio.com/")
                                .getReference("Doctors");

        ValueEventListener dL = new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot d : snapshot.getChildren()) {
                    //String username = d.child("userId").getValue().toString();
                    //Log.i("Username: ", username);


                    StringBuffer sb = new StringBuffer();
                    //casting to arraylist
                    for (String s : (ArrayList<String>) d.child("specs").getValue()) {
                        sb.append(s);
                        sb.append(" ");
                    }

                    String special = sb.toString();
                    String curGender = ((d.child("gender").getValue()).toString()).toLowerCase();
//                    Log.i("paitent wants", docGen);
//                    Log.i("we have:", curGender);
                    if (special.contains(specialization) && curGender.equals(docGen)) {
//                        Log.i("Patient wants:", specialization);
//                        Log.i("We have:", special);
                        String s = "Dr. " + d.child("name").getValue().toString() + " is " + d.child("gender").getValue().toString() + ", Specialist: " + special;
                        names.add(d.child("userId").getValue().toString());
                        everything.add(s);
                    }

                }


                String[] docInfo = everything.toArray(new String[0]);

                ListView lv = (ListView) findViewById(R.id.listDocs);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                                                android.R.layout.simple_list_item_1, docInfo);
                lv.setAdapter(adapter1);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       /* String userIdDoc = names.get(i);
                        Intent intent2 = new Intent(ViewRequestedDoctors.this,
                                        ViewAvailable.class);
                        intent2.putExtra("docUserId", userIdDoc);

                        startActivity(intent2);*/
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };
        ref.addValueEventListener(dL);
    }
}