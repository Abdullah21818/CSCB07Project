package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {
    static Integer n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    public void createNewAccount(View view){
        String number = n.toString();
        String newUserGender;
        RadioButton maleRadioButton = findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = findViewById(R.id.secondChoice);
        if (maleRadioButton.isChecked()) {
            newUserGender = "Male";
        }
        else if (femaleRadioButton.isChecked()) {
            newUserGender = "Female";
        }
        else {
            newUserGender = "Other";
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Patient patient = new Patient(((EditText) findViewById(R.id.newUsername)).getText().toString(),((EditText) findViewById(R.id.newPassword)).getText().toString(),
                ((EditText) findViewById(R.id.newName)).getText().toString(), newUserGender, Integer.parseInt(((EditText) findViewById(R.id.newMonth)).getText().toString())
                ,Integer.parseInt(((EditText) findViewById(R.id.newDay)).getText().toString()),Integer.parseInt(((EditText) findViewById(R.id.newYear)).getText().toString()));

        ref.child("Patients").child(number).setValue(patient);
        n++;
    }
}