package com.example.CSCB07Project;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StyleText {
    public static SpannableStringBuilder makeBold(String boldText, String text) {
        SpannableStringBuilder info = new SpannableStringBuilder(boldText + text);
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
        info.setSpan(bold, 0, boldText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return info;
    }

    public static SpannableStringBuilder formatAppointment(Appointment a, int i) {
        SpannableStringBuilder info = makeBold("— Appointment #" + i + " —", "\n");
        info.append(makeBold("Doctor: ", a.getDoctor() + "\n"));
        info.append(makeBold("Patient: ", a.getPatient() + "\n"));
        info.append(makeBold("Date: ", "From " + a.getStart().toString() + " to "
                    + a.getEnd().toString()));
        return info;
    }

    public static void formatAppointmentView(LinearLayout.LayoutParams l, TextView t, View v) {
        l.setMargins(15,15,15,15);

        t.setTextSize(16);
        t.setLayoutParams(l);

        v.setMinimumWidth(0);
        v.setMinimumHeight(8);
        v.setLayoutParams(l);
    }
}
