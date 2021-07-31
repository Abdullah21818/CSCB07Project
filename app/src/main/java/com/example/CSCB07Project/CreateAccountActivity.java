package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
 

import java.util.LinkedHashSet;


public class CreateAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createNewAccount(View view) {
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
        Date birthday = new Date(Integer.parseInt(((EditText) findViewById(R.id.newMonth)).getText().toString()),
                                 Integer.parseInt(((EditText) findViewById(R.id.newDay)).getText().toString()),
                                 Integer.parseInt(((EditText) findViewById(R.id.newYear)).getText().toString()));

        Patient patient = new Patient(userId, password, name, gender, birthday);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Patients").child(userId).setValue(patient);
        this.finish();
    }
}