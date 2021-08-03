package com.example.CSCB07Project;

import androidx.appcompat.app.AppCompatActivity;

public interface Callback{
    public <DataType> void onCallback(DataType data);
}
