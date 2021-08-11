package com.example.CSCB07Project.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CSCB07Project.Appointment;
import com.example.CSCB07Project.Callback;
import com.example.CSCB07Project.Date;
import com.example.CSCB07Project.FirebaseAPI;
import com.example.CSCB07Project.Patient.Patient;
import com.example.CSCB07Project.PopUp;
import com.example.CSCB07Project.R;
import com.example.CSCB07Project.StyleText;

import java.util.ArrayList;
import java.util.HashMap;

public class BookDoctorTimeSlot extends AppCompatActivity {

    private ArrayList<Date> timeslots;
    private Date selectedDate;
    private int SelectedHour;
    private String docUsername;
    private String patUsername;
    private boolean validTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor_time_slot);

        Intent intent = getIntent();

        docUsername = intent.getStringExtra("docUserId");
        patUsername = intent.getStringExtra("patUserId");
        Log.i("users",patUsername + " Doct:" + docUsername);
        selectedDate = Date.getCurrentTime();
        timeslots = new ArrayList<Date>();
        validTime = false;

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
        if(validTime) {
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
                            , SelectedHour, 0), new Date(month, day, year, SelectedHour + 1, 0));
                    //Log.i("doctorInfo",doctor.getUpcomingAppointments().toString());
                    //Log.i("Appointment", a.getDoctor() + " " + a.getPatient() + " " + a.getStart().toString() + " " + a.getEnd().toString());
                    doctor.addAppointment(a);
                    FirebaseAPI.getPatient(patUsername, new Callback<HashMap<String, Object>>() {
                        @Override
                        public void onCallback(HashMap<String, Object> data) {
                            Patient patient = new Patient(data);
                            patient.addAppointment(a);
                            PopUp.popupMessage(context, "Booking Success", Toast.LENGTH_SHORT);
                            finish();
                        }
                    });
                }
            });
        } else{
            PopUp.popupMessage(getApplicationContext(), "Please select a valid time", Toast.LENGTH_SHORT);
        }
    }

    private void setCalendarView(Doctor doctor) {
        CalendarView c = (CalendarView)findViewById(R.id.calendarView);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Log.i("Updated","tep");
                updateSpinner(new Date(month + 1, dayOfMonth, year), doctor);
            }
        });
    }

    private void updateSpinner(Date date, Doctor doctor) {
        selectedDate = date;
        updateTimeSlots(doctor);
        //Log.i("timeslot",timeslots.toString());
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
                if(spin.getSelectedItem().toString().equals("No Time Available")){
                    validTime = false;
                } else {
                    validTime = true;
                    String[] hourString = spin.getSelectedItem().toString().split(" ");
                    Log.i("Spinner info", hourString[0]);
                    SelectedHour = Integer.parseInt(hourString[0]);
                    if (hourString[1].equals("PM") && SelectedHour != 12)
                        SelectedHour += 12;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void timeslotToStringArray(ArrayList<String> availableDisplay) {
        if(timeslots.isEmpty()){
            availableDisplay.add("No Time Available");
        }
        for (Date date : timeslots) {
            int time = date.getHour();
            String timeString = Integer.toString(time) + " AM";
            if (time > 12)
                timeString = Integer.toString(time - 12) + " PM";
            else if (time == 12)
                timeString = "12 PM";
            availableDisplay.add(timeString);
        }
    }

    private void updateTimeSlots(Doctor doctor) {
        Date currentTime = Date.getCurrentTime();
        Date invalidPeriod = new Date(currentTime.getMonth(), currentTime.getDay(),
                                      currentTime.getYear());
        invalidPeriod.ToNextSunday();
        //Log.i("Work Day?", selectedDate.isWorkDay()+"");
        if(selectedDate.after(invalidPeriod) && selectedDate.isWorkDay()) {
            timeslots = (ArrayList<Date>) doctor.getTimeslots().clone();
            Log.i("doctor timeslot", doctor.getTimeslots().toString());
            for (Appointment appointment : doctor.getUpcomingAppointments()) {
                Date appointmentDate = appointment.getStart();
                if (appointmentDate.sameDay(selectedDate)) {
                    timeslots.remove(new Date(appointmentDate.getHour(), appointmentDate.getMinute()));
                }
            }
        } else {
            timeslots.clear();
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