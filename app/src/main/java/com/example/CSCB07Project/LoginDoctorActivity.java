package com.example.CSCB07Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


public class LoginDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
    }

    public void signIn(View view){
        String userId = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Context context = getApplicationContext();

        AppCompatActivity activity = LoginDoctorActivity.this;
        FirebaseAPI.<String>getData("Doctors/" + userId + "/password", new Callback<String>() {
            @Override
            public void onCallback(String data) {
                if(password.equals(data)) {
                    Intent intent = new Intent(activity, DoctorDashboard.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                } else{
                    PopUp.popupMessage(context, "Invalid Username/Password", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }
}