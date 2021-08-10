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
import java.util.Arrays;
import java.util.List;

public class CreateAccountActivityDoctor extends AppCompatActivity {
    private final static int START_HOUR = 8;
    private final static int END_HOUR = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_doctor);
    }

    public void createNewAccountDoctor(View view){
        boolean validInfo = true;

        String userId = ((EditText) findViewById(R.id.newUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String name = ((EditText) findViewById(R.id.newName)).getText().toString();
        String special = ((EditText) findViewById(R.id.newSpec)).getText().toString();
        String gender;

        RadioButton maleRadioButton = (RadioButton) findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.secondChoice);
        if (maleRadioButton.isChecked())
            gender = "Male";
        else
            gender = "Female";

        if(userId == "" || password == "" || name == ""|| (!maleRadioButton.isChecked() &&
                !femaleRadioButton.isChecked()))
            validInfo = false;

        if(validInfo) {
            FirebaseAPI.getAllUsername("Doctors", new Callback<ArrayList<String>>() {
                @Override
                public void onCallback(ArrayList<String> allUserId) {
                    if (!allUserId.contains(userId)) {
                        CreateDoctor(userId, password, name, special, gender);
                    }
                }
            });
        } else{
            PopUp.popupMessage(getApplicationContext(), "Invalid Info", Toast.LENGTH_SHORT);
        }
    }

    private void CreateDoctor(String userId, String password, String name, String special, String gender) {
        //convert the doctor specializations to arraylist
        String [] elements = special.split(",");
        List<String> fixedLL = Arrays.asList(elements);
        ArrayList<String> specializations = new ArrayList<String>(fixedLL);
        ArrayList<Date> timeslots = new ArrayList<Date>();

        Doctor doctor = new Doctor(userId, password, name, gender, specializations, timeslots);
        timeslotsSetup(timeslots);
        FirebaseAPI.uploadData("Doctors/"+ userId,doctor);

        Intent intent = new Intent(this, LoginDoctorActivity.class);
        startActivity(intent);
    }

    private void timeslotsSetup(ArrayList<Date> timeslots){
        for(int i = START_HOUR; i < END_HOUR; i++){
            timeslots.add(new Date(i, 0));
        }
    }
}