package com.example.CSCB07Project;

import android.content.Context;
import android.widget.Toast;

public class PopUp {
    public static void popupMessage(Context context, String text, int duration){
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
