package com.example.CSCB07Project;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class StyleText {
    public static SpannableStringBuilder makeBold(String boldText, String text) {
        SpannableStringBuilder info = new SpannableStringBuilder(boldText + text);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
        info.setSpan(bold, 0, boldText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return info;
    }

    public static SpannableStringBuilder formatAppointment(Appointment a, int i) {
        SpannableStringBuilder info = makeBold("— Appointment #" + i + " —", "");
        info.append(makeBold("Doctor: ", a.getDoctor() + "\n"));
        info.append(makeBold("Patient: ", a.getPatient() + "\n"));
        info.append(makeBold("Date: ", "From " + a.getStart().toString() + " to "
                    + a.getEnd().toString() + "\n"));
        return info;
    }
}
