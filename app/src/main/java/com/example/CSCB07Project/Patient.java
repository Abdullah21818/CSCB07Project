package com.example.CSCB07Project;

public class Patient extends User {
    private Date birthday;

    public Patient(String userId, String password, String name, String gender, Date birthday) {
        super(userId, password, name, gender);
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public void addAppointment(Appointment a) {
        super.addAppointment(a);
        FirebaseHelper.getDoctor("Doctor", a.doctor).addAppointment(a);
    }
}
