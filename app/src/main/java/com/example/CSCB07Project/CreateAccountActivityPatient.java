package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CreateAccountActivityPatient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_patient);
    }


    public void createNewAccountPatient(View view) {
        boolean validInfo = true;

        String userId = ((EditText) findViewById(R.id.newUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String name = ((EditText) findViewById(R.id.newName)).getText().toString();
        String month = ((EditText) findViewById(R.id.newMonth)).getText().toString();
        String day = ((EditText) findViewById(R.id.newDay)).getText().toString();
        String year = ((EditText) findViewById(R.id.newYear)).getText().toString();
        String gender;

        RadioButton maleRadioButton = findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = findViewById(R.id.secondChoice);
        RadioButton otherRadioButton = findViewById(R.id.thirdChoice);
        if (maleRadioButton.isChecked())
            gender = "Male";
        else if (femaleRadioButton.isChecked())
            gender = "Female";
        else
            gender = "Other";

        if(userId == "" || password == "" || name == "" || (!maleRadioButton.isChecked() &&
            !femaleRadioButton.isChecked() && !otherRadioButton.isChecked()) || month == "" ||
            day == "" || year == "")
            validInfo = false;

        if(validInfo) {
            Date birthday = new Date(Integer.parseInt(month), Integer.parseInt(day),
                    Integer.parseInt(year));
            if(Date.checkValid(birthday)) {
                FirebaseAPI.getAllUsername("Patients", new Callback<ArrayList<String>>() {
                    @Override
                    public void onCallback(ArrayList<String> allUserId) {
                        if (!allUserId.contains(userId)) {
                            createPatient(userId, password, name, gender, birthday);
                        }
                    }
                });
            }
            else {
                PopUp.popupMessage(getApplicationContext(), "Invalid Birthday", Toast.LENGTH_SHORT);
            }
        } else{
            PopUp.popupMessage(getApplicationContext(), "Invalid Info", Toast.LENGTH_SHORT);
        }
    }

    private void createPatient(String userId, String password, String name, String gender, Date birthday) {
        Patient patient = new Patient(userId, password, name, gender, birthday);
        FirebaseAPI.uploadData("Patients/"+userId, patient);

        Intent intent = new Intent(this, LoginPatientActivity.class);
        startActivity(intent);
    }
}