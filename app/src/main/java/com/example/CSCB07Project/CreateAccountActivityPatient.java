package com.example.CSCB07Project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateAccountActivityPatient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_patient);
    }

    public void creatNewAccount(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreateAccountActivityPatient.this);
        dialog.setMessage("Are you sure?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createNewAccountPatient(view);
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    public void createNewAccountPatient(View view) {
        String gender;
        RadioButton maleRadioButton = findViewById(R.id.firstChoice);
        RadioButton femaleRadioButton = findViewById(R.id.secondChoice);

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

        FirebaseAPI.uploadData("Patients/"+userId, patient);
        Toast.makeText(CreateAccountActivityPatient.this, "Created successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginPatientActivity.class);
        startActivity(intent);
    }
}