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

        String newUserGender="";
        RadioButton maleRadioButton = (RadioButton) findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.secondChoice);

        if(maleRadioButton.isChecked()){

            newUserGender = "Male";
        }
        else if (femaleRadioButton.isChecked()) {
            newUserGender = "Female";
        }
        else {
            newUserGender = "Other";
        }
        String userId = ((EditText) findViewById(R.id.newUsername)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String name = ((EditText) findViewById(R.id.newName)).getText().toString();
        String special = ((EditText) findViewById(R.id.newSpec)).getText().toString();

        //convert the doctor specializations to arraylist
        String [] elements = special.split(",");
        List<String> fixedLL = Arrays.asList(elements);

        ArrayList <String> specializations = new ArrayList<String>(fixedLL);

        Doctor doctor = new Doctor(userId,password,name,newUserGender,specializations);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Doctors").child(userId).setValue(doctor);

//        ref.child("Patients").child(userId).child("userId").setValue(userId);
//        ref.child("Patients").child(userId).child("password").setValue(password);
//        ref.child("Patients").child(userId).child("name").setValue(name);

        Intent intent = new Intent(this, LoginDoctorActivity.class);
        startActivity(intent);
        this.finish();



    }
}