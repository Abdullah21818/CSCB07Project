package com.example.CSCB07Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BookDoctorTimeSlot extends AppCompatActivity {

    private ArrayList<Date> timeslots;
    private Date selectedDate;
    private int SelectedHour;
    private String docUsername;
    private String patUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor_time_slot);

        Intent intent = getIntent();

        docUsername = intent.getStringExtra("docUserId");
        patUsername = intent.getStringExtra("patUserId");
        selectedDate = Date.getCurrentTime();

        FirebaseAPI.getDoctor(docUsername, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);
                updateTimeSlots(doctor);
                setDoctorInfo(doctor);
                updateSpinner(selectedDate, doctor);
                setCalendarView(doctor);
            }
        });
    }

    public void bookAppointment(View view){
        Context context = getApplicationContext();
        FirebaseAPI.getDoctor(docUsername, new Callback<HashMap<String, Object>>() {
            @Override
            public void onCallback(HashMap<String, Object> data) {
                Doctor doctor = new Doctor(data);
                //get month, day, and year
                int month = selectedDate.getMonth();
                int day = selectedDate.getDay();
                int year = selectedDate.getYear();

                Appointment a = new Appointment(docUsername, patUsername, new Date(month, day, year
                , SelectedHour,0), new Date(month, day, year, SelectedHour + 1,0));
                //Log.i("doctorInfo",doctor.getUpcomingAppointments().toString());
                //Log.i("Appointment", a.getDoctor() + " " + a.getPatient() + " " + a.getStart().toString() + " " + a.getEnd().toString());
                doctor.addAppointment(a);
                FirebaseAPI.getPatient(patUsername, new Callback<HashMap<String, Object>>() {
                    @Override
                    public void onCallback(HashMap<String, Object> data) {
                        Patient patient = new Patient(data);
                        patient.addAppointment(a);
                        PopUp.popupMessage(context, "Booked Appointment", Toast.LENGTH_SHORT);
                        finish();
                    }
                });
            }
        });
    }

    private void setCalendarView(Doctor doctor) {
        CalendarView c = (CalendarView)findViewById(R.id.calendarView);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int dayOfMonth) {
                updateSpinner(new Date(month + 1, dayOfMonth, year), doctor);
            }
        });
    }

    private void updateSpinner(Date date, Doctor doctor) {
        selectedDate = date;
        updateTimeSlots(doctor);
        ArrayList<String> availableDisplay = new ArrayList<String>();
        timeslotToStringArray(availableDisplay);

        Spinner spin = (Spinner) findViewById(R.id.availableTime);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, availableDisplay.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get hour
                Spinner spin = (Spinner) findViewById(R.id.availableTime);
                String[] hourString = spin.getSelectedItem().toString().split(" ");
                Log.i("Spinner info", hourString[0]);
                SelectedHour = Integer.parseInt(hourString[0]);
                if(hourString[1].equals("PM") && SelectedHour != 12)
                    SelectedHour += 12;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void timeslotToStringArray(ArrayList<String> availableDisplay) {
        for(Date date : timeslots){
            int time = date.getHour();
            String timeString = Integer.toString(time) + " AM";
            if(time > 12)
                timeString = Integer.toString(time - 12) + " PM";
            else if(time == 12)
                timeString = "12 PM";
            availableDisplay.add(timeString);
        }
    }

    private void updateTimeSlots(Doctor doctor) {
        timeslots = doctor.getTimeslots();
        for(Appointment appointment : doctor.getUpcomingAppointments()){
            Date appointmentDate = appointment.start;
            if(appointmentDate.sameDay(selectedDate) || selectedDate.beforeThis(Date.getCurrentTime())){
                timeslots.remove(new Date(appointmentDate.getHour(), appointmentDate.getMinute()));
            }
        }
    }

    private void setDoctorInfo(Doctor doctor) {
        TextView t = (TextView)findViewById(R.id.DoctorInfo);
        SpannableStringBuilder info = StyleText.makeBold("Name: ", doctor.getName());
        info.append(StyleText.makeBold("\nGender: ", doctor.getGender()));
        info.append(StyleText.makeBold("\nSpecializations: ",""));
        ArrayList<String> specializations = doctor.getSpecs();
        for (int i = 0; i < specializations.size(); i++) {
            info.append(specializations.get(i));
            if (i + 1 < specializations.size()) {
                info.append(", ");
            }
        }
        t.setText(info);
    }
}