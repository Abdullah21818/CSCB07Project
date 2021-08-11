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

import java.util.ArrayList;


public class LoginDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
    }

    public String getUserId() {
        return ((EditText) findViewById(R.id.username)).getText().toString();
    }

    public String getPassword() {
        return ((EditText) findViewById(R.id.password)).getText().toString();
    }

    public void displayMessage(String message) {
        Context context = getApplicationContext();
        PopUp.popupMessage(context, message, Toast.LENGTH_SHORT);
    }

    public void signIn(View view){
        LoginDoctorPresenter presenter = new LoginDoctorPresenter(new FirebaseAPI(), this);
        presenter.checkUsernamePassword();
    }

    public void toDashboard() {
        AppCompatActivity activity = this;
        Intent intent = new Intent(activity, DoctorDashboard.class);
        intent.putExtra("userId", getUserId());
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivityDoctor.class);
        startActivity(intent);
    }
}