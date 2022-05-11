package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class control_system extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_system);
    }

    public void gotopatientpage(View view) {
        Intent add=new Intent(this,patient_activity.class);
        startActivity(add);
    }

    public void gotodelete(View view) {
        Intent delete=new Intent(this,delete_patient.class);
        startActivity(delete);
    }

    public void gotoprintpage(View view) {
        Intent print=new Intent(this,printing_page.class);
        startActivity(print);
    }
}