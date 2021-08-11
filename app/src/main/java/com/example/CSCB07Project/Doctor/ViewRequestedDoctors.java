package com.example.CSCB07Project.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.View;

import com.example.CSCB07Project.Callback;
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

        /*
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

                        startActivity(intent2);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println(error);
            }
        };
        ref.addValueEventListener(dL);*/
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