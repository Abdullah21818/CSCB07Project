package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityPatient.class);
        startActivity(intent);
    }

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        AppCompatActivity activity = this;
        FirebaseAPI.<String>getData(ref, "Patients/" + userId + "/password", new Callback() {
            @Override
            public void onCallback(Object data) {
                if(password.equals(data)) {
                    Intent intent = new Intent(activity, DoctorDashboard.class);
                    startActivity(intent);
                }
            }
        });
    }
}