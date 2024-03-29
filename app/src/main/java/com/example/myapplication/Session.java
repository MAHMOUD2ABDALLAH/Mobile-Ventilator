package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.custom.CustomSheetDialog;
import com.example.myapplication.data.SharedPrefManager;
import com.example.myapplication.data.model.Disease;
import com.example.myapplication.data.model.Doctor;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivitySessionBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class Session extends AppCompatActivity implements CustomSheetDialog.CustomSheetDialogListener {
    private static final String TAG = "Session";
    private final ArrayList<Disease> diseases = new ArrayList<>();
    private ActivitySessionBinding binding;
    private VentilatorSession newSession;
    private CustomSheetDialog customSheetDialog;
    private SharedPrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int min_val1 = 60;
        int max_val1 = 180;
        int heartrate = (int) (Math.random() * (max_val1 - min_val1));

        float min_val2 = 0.88f;
        float max_val2 = 1.0f;
        float oxygen = (float) (Math.random() * (max_val2 - min_val2));

        float min_val3 = 0.20f;
        float max_val3 = 0.41f;
        float temperature = (float) (Math.random() * (max_val3 - min_val3));

        manager = new SharedPrefManager(this);
        Doctor doctor = manager.getDoctor();
        String currentPatientNationalID = getIntent().getExtras().getString("nationalID");
        newSession = new VentilatorSession(currentPatientNationalID,
                heartrate, oxygen, temperature, doctor.getOrganizationID(), doctor.getFullName());

        String pbm = newSession.getHeartRate() + " PBM";
        binding.textView22.setText(pbm);

        String oxi = (newSession.getOxygenPercentage() * 100) + " %";
        binding.textView23.setText(oxi);

        String temp = newSession.getTemperature() + " °C";
        binding.textView24.setText(temp);

        getDiseases();


        for (int i = 0; i < diseases.size(); i++) {
            Disease disease = diseases.get(i);
            if ((int) disease.getHeartRate().get("low") <= newSession.getHeartRate() && newSession.getHeartRate() <= (int) disease.getHeartRate().get("high")
                    && (float) disease.getOxygen().get("low") <= newSession.getOxygenPercentage() && newSession.getOxygenPercentage() <= (float) disease.getOxygen().get("high")
                    && (float) disease.getTemperature().get("low") <= newSession.getTemperature() && newSession.getTemperature() <= (float) disease.getTemperature().get("high")) {
                binding.textView9.setText(disease.getName());
                newSession.setIllness(disease.getName());
                break;
            } else {
                binding.textView9.setText("New Target");
                newSession.setIllness("New Target");
            }
        }

        customSheetDialog = new CustomSheetDialog(this);
        binding.button11.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
                    intent.putExtra("newSession", newSession);
                    startActivity(intent);
                    finish();
                }
        );
        binding.button5.setOnClickListener(v -> customSheetDialog.show(getSupportFragmentManager(), ""));
    }

    private void getDiseases() {
        HashMap<String, Object> heartRate = new HashMap<>();
        heartRate.put("low", 60);
        heartRate.put("high", 100);
        HashMap<String, Object> oxygen = new HashMap<>();
        oxygen.put("low", 0.9f);
        oxygen.put("high", 1.0f);
        HashMap<String, Object> temperature = new HashMap<>();
        temperature.put("low", 0.361f);
        temperature.put("high", 0.372f);

        diseases.add(new Disease(
                "Normal",
                heartRate,
                oxygen,
                temperature
        ));

        HashMap<String, Object> asthmaHeartRate = new HashMap<>();
        asthmaHeartRate.put("low", 120);
        asthmaHeartRate.put("high", 150);
        HashMap<String, Object> asthmaOxygen = new HashMap<>();
        asthmaOxygen.put("low", 0.95f);
        asthmaOxygen.put("high", 1.0f);
        HashMap<String, Object> asthmaTemperature = new HashMap<>();
        asthmaTemperature.put("low", 0.2f);
        asthmaTemperature.put("high", 0.216f);
        diseases.add(new Disease(
                "Asthma",
                asthmaHeartRate,
                asthmaOxygen,
                asthmaTemperature
        ));

        HashMap<String, Object> pneumoniaHeartRate = new HashMap<>();
        pneumoniaHeartRate.put("low", 150);
        pneumoniaHeartRate.put("high", 180);
        HashMap<String, Object> pneumoniaOxygen = new HashMap<>();
        pneumoniaOxygen.put("low", 0.91f);
        pneumoniaOxygen.put("high", 0.94f);
        HashMap<String, Object> pneumoniaTemperature = new HashMap<>();
        pneumoniaTemperature.put("low", 0.4f);
        pneumoniaTemperature.put("high", 0.405f);
        diseases.add(new Disease(
                "Pneumonia",
                pneumoniaHeartRate,
                pneumoniaOxygen,
                pneumoniaTemperature
        ));

        HashMap<String, Object> covid19HeartRate = new HashMap<>();
        covid19HeartRate.put("low", 100);
        covid19HeartRate.put("high", 120);
        HashMap<String, Object> covid19Oxygen = new HashMap<>();
        covid19Oxygen.put("low", 0.88f);
        covid19Oxygen.put("high", 0.92f);
        HashMap<String, Object> covid19Temperature = new HashMap<>();
        covid19Temperature.put("low", 0.378f);
        covid19Temperature.put("high", 0.392f);
        diseases.add(new Disease(
                "Covid-19",
                covid19HeartRate,
                covid19Oxygen,
                covid19Temperature
        ));
    }

    @Override
    public void onCustomSelected(int custom) {
        newSession.setVentilatorOxi(custom);
        String ventilatorOxi = newSession.getVentilatorOxi() + "%";
        binding.textView25.setText(ventilatorOxi);
    }
}