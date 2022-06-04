package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FirstChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_choice_page);
    }


    public void gotopatientpage(View view) {
        Intent in=new Intent(this, PatientActivity.class);
        startActivity(in);
    }

    public void gotologinpage(View view) {
        Intent in2=new Intent(this, LoginActivity.class);
        startActivity(in2);
    }
}