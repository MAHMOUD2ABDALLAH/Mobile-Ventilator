package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Session extends AppCompatActivity {
    private static final String TAG = "Session";
    private String currentPatientNationalID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        currentPatientNationalID=getIntent().getExtras().getString("nationalID");
        Log.e(TAG, "currentPatientNationalID: "+currentPatientNationalID );
    }

}