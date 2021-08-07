package com.example.CSCB07Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Date;


public class LoginDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
        // full folder
    }



    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        AppCompatActivity activity = LoginDoctorActivity.this;
        FirebaseAPI.<String>getData("Doctors/" + userId + "/password", new Callback<String>() {
            @Override
            public void onCallback(String data) {
                if(password.equals(data)) {
                    DatabaseReference D_ref = FirebaseDatabase.getInstance().getReference().child("Doctors");
                    Date time = new Date(System.currentTimeMillis());
                    com.example.CSCB07Project.Date t = new com.example.CSCB07Project.Date(time);
                    D_ref.child(userId).child("login_time").setValue(t);
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()){
                                Doctor doctor = child.getValue(Doctor.class);
                                if (doctor.upcomingAppoint == null){
                                    return;
                                }
                                for (Appointment apt : doctor.upcomingAppoint){
                                    if (apt.end.before(t)){
                                        doctor.upcomingAppoint.remove(apt);
                                        doctor.visited.add(apt.toString());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w("warning", "on cancelled");
                        }
                    };
                    D_ref.addValueEventListener(listener);
                    Intent intent = new Intent(activity, DoctorDashboard.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
            }
        });
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }
}