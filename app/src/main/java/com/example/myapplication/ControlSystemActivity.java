package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.SharedPrefManager;
import com.example.myapplication.viewPatient.ViewPatientActivity;

public class ControlSystemActivity extends AppCompatActivity {

    private SharedPrefManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_system);
        manager=new SharedPrefManager(this);
    }

    public void gotopatientpage(View view) {
        Intent add=new Intent(this, PatientActivity.class);
        startActivity(add);
    }

    public void gotodelete(View view) {
        Intent delete=new Intent(this, ViewPatientActivity.class);
        startActivity(delete);
    }

    public void gotosplashview(View view) {
        Intent cluster=new Intent(this, ClusteringActivity.class);
        startActivity(cluster);

    }

    public void gotologinpage(View view) {
        manager.clearData();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}