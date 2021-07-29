package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //need to specify some action to be done when an acitivity
    //is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //READING AND WRITING FROM LOCAL FILES
        /*
        //Writing to a file
        try{
            FileOutputStream fos = openFileOutput("myfile.txt", MODE_PRIVATE);
            String text = "hi";
            fos.write(text.getBytes());
            fos.close();
        }
        catch(Exception ex){
        }

        //Reading from a file
        try {
            FileInputStream fis = openFileInput("myfile.txt");
            BufferedReader b = new BufferedReader(new InputStreamReader(fis));
            //reads line and moves cursor to second line, call again to keep moving
            Log.i("info",b.readLine());
//            //Reading all the lines
//            String s = b.readLine();
//            while(s!=null){
//                //processing
//                s = b.readLine();
//            }
            fis.close();
        }  catch (Exception ex) {
        }
        */

        //USING PREFERENCES TO DO STUFF
        //writing to preferences
        /*
        SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences p = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putInt("mykey", 123);
        editor.apply();

        //reading preferences
        SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        int value = p.getInt("mykey",0);
        Log.i("info", ""+value);
        */

        //WRITING TO FIREBASE DATABASE

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Student student = new Student(100, "alice");
        //data is stored as key value pairs
        //child("s1").setValue(student); s1 is key, value is value
        //we are adding this under a node called students
        //students is under reference
        ref.child("students").child("s1").setValue(student);

        /*
        //Reading from the realtime database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("students");
        ValueEventListener listener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                //what to do if the data changes, here we just display all info
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Student student = child.getValue(Student.class);
                    Log.i("info", student.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
        */
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        Switch sw = (Switch) findViewById(R.id.switch1);
//        boolean b =sw.isChecked();
        CheckBox cb = (CheckBox)findViewById(R.id.checkBox) ;
        boolean b = cb.isChecked();
        intent.putExtra(EXTRA_MESSAGE, ""+b);

        startActivity(intent);
    }
}