package com.example.CSCB07Project;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.CSCB07Project.PatientFiles.Patient;

public class StyleText {
    public static SpannableStringBuilder makeBold(String boldText, String text) {
        SpannableStringBuilder info = new SpannableStringBuilder(boldText + text);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
        info.setSpan(bold, 0, boldText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return info;
    }

    public static SpannableStringBuilder formatAppointment(Appointment a, int i) {
        SpannableStringBuilder info = makeBold("— Appointment #" + i + " —", "\n");
        info.append(makeBold("Doctor Username: ", a.getDoctor() + "\n"));
        info.append(makeBold("Patient Username: ", a.getPatient() + "\n"));
        info.append(makeBold("Date: ", "From " + a.getStart().toString() + " to "
                    + a.getEnd().toString()));
        return info;
    }

    public static SpannableStringBuilder formatPatientInfo(Patient patient) {
        SpannableStringBuilder info = makeBold("Username: ", patient.getUserId());
        info.append(makeBold("\nName: ", patient.getName()));
        info.append(makeBold("\nGender: ", patient.getGender()));
        info.append(makeBold("\nBirthday: ",
                            patient.getBirthday().toString()));
        info.append(makeBold("\nDoctor(s) Visited: ", patient.getVisited().toString()));
        return info;
    }

    public static void formatNotice(SpannableStringBuilder s, int l) {
        RelativeSizeSpan big = new RelativeSizeSpan(1.2f);
        s.setSpan(big, 0, l, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }
}
