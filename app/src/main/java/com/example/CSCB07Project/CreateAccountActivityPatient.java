package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateAccountActivityPatient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_patient);
    }


    public void createNewAccountPatient(View view){

        String gender;
        RadioButton maleRadioButton = (RadioButton) findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.secondChoice);

        if(maleRadioButton.isChecked()){
          
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
        Date birthday = new Date(Integer.parseInt(((EditText) findViewById(R.id.newMonth)).getText().toString()),
                                 Integer.parseInt(((EditText) findViewById(R.id.newDay)).getText().toString()),
                                 Integer.parseInt(((EditText) findViewById(R.id.newYear)).getText().toString()));


        Patient patient = new Patient(userId, password, name, gender, birthday);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("Patients").child(userId).setValue(patient);
        /*
        ref.child("Patients").child(userId).child("userId").setValue(userId);
        ref.child("Patients").child(userId).child("password").setValue(password);
        ref.child("Patients").child(userId).child("name").setValue(name);
        ref.child("Patients").child(userId).child("birthday").setValue(birthday);
         */

        Intent intent = new Intent(this, LoginPatientActivity.class);
        startActivity(intent);
        this.finish();


     
    }
}