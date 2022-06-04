package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ControlSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_system);
    }

    public void gotopatientpage(View view) {
        Intent add=new Intent(this, PatientActivity.class);
        startActivity(add);
    }

    public void gotodelete(View view) {
        Intent delete=new Intent(this, DeletePatientActivity.class);
        startActivity(delete);
    }

    public void gotoprintpage(View view) {
        Intent print=new Intent(this, PrintingActivity.class);
        startActivity(print);
    }

    public void gotosplashview(View view) {
        Intent cluster=new Intent(this, ClusteringActivity.class);
        startActivity(cluster);

    }
}