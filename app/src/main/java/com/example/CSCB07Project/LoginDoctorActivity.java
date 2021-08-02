package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String firebasePassword = FirebaseHelper.getValue(ref, "Doctors/"+userId+"/password");
/*
        Doctor test = FirebaseHelper.getValue(ref.child("Doctors").child("abs"));
        if(test != null)
            Log.i("Doctor: ", test.toString());
        else
            Log.i("nono: ","Doctor not found");*/

        if(password != null && password.equals(firebasePassword)) {
            Intent intent = new Intent(this, DoctorDashboard.class);
            startActivity(intent);
        }
    }
}