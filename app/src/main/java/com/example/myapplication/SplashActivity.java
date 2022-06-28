package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.SharedPrefManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private SharedPrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new SharedPrefManager(this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (manager.getDoctor() != null)
                startActivity(new Intent(getApplicationContext(), ControlSystemActivity.class));
            else
                startActivity(new Intent(getApplicationContext(), FirstChoiceActivity.class));
        }, 2000);
    }
}