package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Bundle;


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
}