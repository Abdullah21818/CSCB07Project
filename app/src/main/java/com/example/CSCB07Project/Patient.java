package com.example.CSCB07Project;

public class Patient extends User {
    private Date birthday;

    public Patient(String userId, String password, String name, String gender, Date birthday){

    //public Patient(String userId, String password, String name, String gender,
                   //int month, int day, int year) {

        super(userId, password, name, gender);
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setAppointment(Appointment a) {
        upcomingAppoint.add(a);
        a.doctor.setAppointment(a);
    }

}
