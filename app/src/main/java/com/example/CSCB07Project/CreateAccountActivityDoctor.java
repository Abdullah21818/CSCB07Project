package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateAccountActivityDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_doctor);
    }


    public void createNewAccountDoctor(View view){
        String gender;
        RadioButton maleRadioButton = (RadioButton) findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.secondChoice);

        if (maleRadioButton.isChecked()) {
            gender = "Male";
        }
        else if (femaleRadioButton.isChecked()) {
            gender = "Female";
        }
        else {
            gender = "Other";
        }

        String userId = ((EditText) findViewById(R.id.newUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String name = ((EditText) findViewById(R.id.newName)).getText().toString();
        String special = ((EditText) findViewById(R.id.newSpec)).getText().toString();

        //convert the doctor specializations to arraylist
        String [] elements = special.split(",");
        List<String> fixedLL = Arrays.asList(elements);
        ArrayList <String> specializations = new ArrayList<String>(fixedLL);

        Doctor doctor = new Doctor(userId,password,name,gender, specializations);

        FirebaseAPI.uploadData("Doctors/"+userId,doctor);
        /*Creating a doctor with timeslots for testing purposes
        ArrayList<Date> tim = new ArrayList<Date>();
        tim.add(new Date(1, 2, 3));
        FirebaseAPI.uploadData("Doctors/testin",new Doctor("testin","1","1",
                "male",new ArrayList<String>(), new ArrayList<Appointment>(), specializations,
                tim));*/

        Intent intent = new Intent(this, LoginDoctorActivity.class);
        startActivity(intent);
    }
}