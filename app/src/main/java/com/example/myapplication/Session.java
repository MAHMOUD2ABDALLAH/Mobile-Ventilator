package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.custom.CustomSheetDialog;
import com.example.myapplication.data.model.Disease;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivitySessionBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class Session extends AppCompatActivity implements CustomSheetDialog.CustomSheetDialogListener {
    private static final String TAG = "Session";
    private final ArrayList<Disease> diseases = new ArrayList<>();
    private ActivitySessionBinding binding;
    private VentilatorSession newSession;
    private CustomSheetDialog customSheetDialog;
    private CollectionReference reference;
    private String currentSessionId;
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    if (data != null) {
                        HashMap<String, String> h = (HashMap<String, String>) data.getSerializableExtra("symptoms");
                        Log.e(TAG, "result: " + h);
                        newSession.setSymptoms(h);
                        reference.document(currentSessionId).set(newSession, SetOptions.merge());
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference=FirebaseFirestore.getInstance().collection("VentilatorSessions");
        currentSessionId=reference.document().getId();

        String currentPatientNationalID = getIntent().getExtras().getString("nationalID");
        newSession = new VentilatorSession(currentPatientNationalID,
                150, 0.91f, 0.402f);

        String pbm = newSession.getHeartRate() + " PBM";
        binding.textView22.setText(pbm);

        String oxi = (newSession.getOxygenPercentage() * 100) + " %";
        binding.textView23.setText(oxi);

        String temp = newSession.getTemperature() + " °C";
        binding.textView24.setText(temp);

        getDiseases();


        for (int i = 0; i < diseases.size(); i++) {
            Disease disease= diseases.get(i);
            if (disease.getHeartRate().get("low") <= newSession.getHeartRate() && newSession.getHeartRate() <= disease.getHeartRate().get("high")) {
                if (disease.getOxygen().get("low") <= newSession.getOxygenPercentage() && newSession.getOxygenPercentage() <= disease.getOxygen().get("high")) {
                    if (disease.getTemperature().get("low") <= newSession.getTemperature() && newSession.getTemperature() <= disease.getTemperature().get("high")) {
                        binding.textView9.setText(disease.getName());
                        newSession.setIllness(disease.getName());
                        break;
                    }
                }
            } else {
                binding.textView9.setText("New Target");
                newSession.setIllness("New Target");
            }
            if (i==diseases.size()-1)
                reference.document(currentSessionId).set(newSession);
        }

        customSheetDialog = new CustomSheetDialog(this);
        binding.button11.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
                    someActivityResultLauncher.launch(intent);
                }
        );
        binding.button5.setOnClickListener(v -> customSheetDialog.show(getSupportFragmentManager(), ""));
    }

    private void getDiseases() {
        HashMap<String, Integer> heartRate = new HashMap<>();
        heartRate.put("low", 60);
        heartRate.put("high", 100);
        HashMap<String, Float> oxygen = new HashMap<>();
        oxygen.put("low", 0.9f);
        oxygen.put("high", 1.0f);
        HashMap<String, Float> temperature = new HashMap<>();
        temperature.put("low", 0.361f);
        temperature.put("high", 0.372f);
        diseases.add(new Disease(
                "Normal",
                heartRate,
                oxygen,
                temperature
        ));

        HashMap<String, Integer> asthmaHeartRate = new HashMap<>();
        asthmaHeartRate.put("low", 120);
        asthmaHeartRate.put("high", 150);
        HashMap<String, Float> asthmaOxygen = new HashMap<>();
        asthmaOxygen.put("low", 0.95f);
        asthmaOxygen.put("high", 1.0f);
        HashMap<String, Float> asthmaTemperature = new HashMap<>();
        asthmaTemperature.put("low", 0.2f);
        asthmaTemperature.put("high", 0.216f);
        diseases.add(new Disease(
                "Asthma",
                asthmaHeartRate,
                asthmaOxygen,
                asthmaTemperature
        ));

        HashMap<String, Integer> pneumoniaHeartRate = new HashMap<>();
        pneumoniaHeartRate.put("low", 150);
        pneumoniaHeartRate.put("high", 180);
        HashMap<String, Float> pneumoniaOxygen = new HashMap<>();
        pneumoniaOxygen.put("low", 0.91f);
        pneumoniaOxygen.put("high", 0.94f);
        HashMap<String, Float> pneumoniaTemperature = new HashMap<>();
        pneumoniaTemperature.put("low", 0.4f);
        pneumoniaTemperature.put("high", 0.405f);
        diseases.add(new Disease(
                "Pneumonia",
                pneumoniaHeartRate,
                pneumoniaOxygen,
                pneumoniaTemperature
        ));

        HashMap<String, Integer> covid19HeartRate = new HashMap<>();
        covid19HeartRate.put("low", 100);
        covid19HeartRate.put("high", 120);
        HashMap<String, Float> covid19Oxygen = new HashMap<>();
        covid19Oxygen.put("low", 0.88f);
        covid19Oxygen.put("high", 0.92f);
        HashMap<String, Float> covid19Temperature = new HashMap<>();
        covid19Temperature.put("low", 0.378f);
        covid19Temperature.put("high", 0.392f);
        diseases.add(new Disease(
                "Covid-19",
                covid19HeartRate,
                covid19Oxygen,
                covid19Temperature
        ));
//        FirebaseFirestore.getInstance().collection("Diseases")
//                .get().addOnSuccessListener(queryDocumentSnapshots -> {
//            Log.e(TAG, "getDiseases: hooooo"+queryDocumentSnapshots.getDocuments().size() );
//                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
//                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
//                            Disease disease = snapshot.toObject(Disease.class);
//                            Log.e(TAG, "getDiseases: "+disease );
//                            if (disease != null) {
//                                disease.setName(snapshot.getId());
//                                diseases.add(disease);
//                                Log.e(TAG, "onSuccess: "+diseases );
//                            }
//                        }
//                    }
//                }).addOnFailureListener(e -> Log.e(TAG, "onFailure: "+e ));
    }

    @Override
    public void onCustomSelected(float custom) {
        newSession.setVentilatorOxi(custom);
        String ventilatorOxi = newSession.getVentilatorOxi() + "%";
        binding.textView25.setText(ventilatorOxi);
        reference.document(currentSessionId).set(newSession, SetOptions.merge());
    }
}