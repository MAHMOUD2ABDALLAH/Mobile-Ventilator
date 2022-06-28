package com.example.myapplication.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.data.model.Doctor;
import com.google.gson.Gson;

public class SharedPrefManager {
    SharedPreferences sharedPreferences;

    public SharedPrefManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
    }

    public void saveDoctor(Doctor doctor){
         sharedPreferences.edit().putString("doctor",new Gson().toJson(doctor)).apply();
    }

    public Doctor getDoctor(){
        return new Gson().fromJson(sharedPreferences.getString("doctor",""),Doctor.class);
    }

    public void clearData(){
        sharedPreferences.edit().clear().apply();
    }
}
