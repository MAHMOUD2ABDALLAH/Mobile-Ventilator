package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.myapplication.data.model.Disease;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivityClusteringBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClusteringActivity extends AppCompatActivity {
    private static final String TAG = "ClusteringActivity";
    private ActivityClusteringBinding binding;
    private final ArrayList<Disease> diseases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClusteringBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDiseases();

        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
            Python py =Python.getInstance();
            final PyObject pyobj=py.getModule("script");// here we will give name of our python file
            pyobj.
        }

        getVentilatorSessions();
    }

    private void getVentilatorSessions() {
        FirebaseFirestore.getInstance().collection("VentilatorSessions")
                .get().addOnSuccessListener(snapshots -> {
            if (snapshots.getDocuments().size() > 0) {
                ArrayList<VentilatorSession> sessions = new ArrayList<>();
                for (int i = 0; i < snapshots.getDocuments().size(); i++) {
                    VentilatorSession session = snapshots.getDocuments().get(i).toObject(VentilatorSession.class);
                    if (session != null)
                        sessions.add(session);
                    if (i == snapshots.getDocuments().size() - 1) {
                        binding.progressBar.setVisibility(View.GONE);
                        List<Integer> heartRates = Stream.of(sessions).map(VentilatorSession::getHeartRate)
                                .collect(Collectors.toList());
                        List<Float> oxygenPercentages = Stream.of(sessions).map(VentilatorSession::getOxygenPercentage)
                                .collect(Collectors.toList());
                        List<Float> temperatures = Stream.of(sessions).map(VentilatorSession::getTemperature)
                                .collect(Collectors.toList());

                    }
                }
            }
        });
    }

    private void getDiseases() {
        HashMap<String, Object> heartRate = new HashMap<>();
        heartRate.put("low", 60);
        heartRate.put("high", 100);
        heartRate.put("average",(60+100)/2);
        HashMap<String, Object> oxygen = new HashMap<>();
        oxygen.put("low", 0.9f);
        oxygen.put("high", 1.0f);
        oxygen.put("average",( 0.9f+ 1.0f)/2);
        HashMap<String, Object> temperature = new HashMap<>();
        temperature.put("low", 0.361f);
        temperature.put("high", 0.372f);
        temperature.put("average",( 0.361f+ 0.372f)/2);
        diseases.add(new Disease(
                "Normal",
                heartRate,
                oxygen,
                temperature
        ));

        HashMap<String, Object> asthmaHeartRate = new HashMap<>();
        asthmaHeartRate.put("low", 120);
        asthmaHeartRate.put("high", 150);
        asthmaHeartRate.put("average",( 120+ 150)/2);
        HashMap<String, Object> asthmaOxygen = new HashMap<>();
        asthmaOxygen.put("low", 0.95f);
        asthmaOxygen.put("high", 1.0f);
        asthmaOxygen.put("average",( 0.95f+ 1.0f)/2);
        HashMap<String, Object> asthmaTemperature = new HashMap<>();
        asthmaTemperature.put("low", 0.2f);
        asthmaTemperature.put("high", 0.216f);
        asthmaTemperature.put("average",( 0.2f+ 0.216f)/2);
        diseases.add(new Disease(
                "Asthma",
                asthmaHeartRate,
                asthmaOxygen,
                asthmaTemperature
        ));

        HashMap<String, Object> pneumoniaHeartRate = new HashMap<>();
        pneumoniaHeartRate.put("low", 150);
        pneumoniaHeartRate.put("high", 180);
        pneumoniaHeartRate.put("average",( 150+ 180)/2);
        HashMap<String, Object> pneumoniaOxygen = new HashMap<>();
        pneumoniaOxygen.put("low", 0.91f);
        pneumoniaOxygen.put("high", 0.94f);
        pneumoniaOxygen.put("average",( 0.91f+  0.94f)/2);
        HashMap<String, Object> pneumoniaTemperature = new HashMap<>();
        pneumoniaTemperature.put("low", 0.4f);
        pneumoniaTemperature.put("high", 0.405f);
        pneumoniaTemperature.put("average",( 0.4f+  0.405f)/2);
        diseases.add(new Disease(
                "Pneumonia",
                pneumoniaHeartRate,
                pneumoniaOxygen,
                pneumoniaTemperature
        ));

        HashMap<String, Object> covid19HeartRate = new HashMap<>();
        covid19HeartRate.put("low", 100);
        covid19HeartRate.put("high", 120);
        covid19HeartRate.put("average",( 100+  120)/2);
        HashMap<String, Object> covid19Oxygen = new HashMap<>();
        covid19Oxygen.put("low", 0.88f);
        covid19Oxygen.put("high", 0.92f);
        covid19Oxygen.put("average",( 0.88f+  0.92f)/2);
        HashMap<String, Object> covid19Temperature = new HashMap<>();
        covid19Temperature.put("low", 0.378f);
        covid19Temperature.put("high", 0.392f);
        covid19Temperature.put("average",( 0.378f+  0.392f)/2);
        diseases.add(new Disease(
                "Covid-19",
                covid19HeartRate,
                covid19Oxygen,
                covid19Temperature
        ));
    }

}