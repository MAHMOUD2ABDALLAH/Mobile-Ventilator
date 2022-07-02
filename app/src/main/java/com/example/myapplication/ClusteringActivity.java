package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.myapplication.data.model.Disease;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivityClusteringBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClusteringActivity extends AppCompatActivity {
    private static final String TAG = "ClusteringActivity";
    private ActivityClusteringBinding binding;
    private final ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<VentilatorSession> sessions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClusteringBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.pie.setCenterText("Ventilator\nDistribution");
        binding.pie.setCenterTextSize(22f);
        getDiseases();
        getVentilatorSessions();
    }

    private void getVentilatorSessions() {
        FirebaseFirestore.getInstance().collection("VentilatorSessions")
                .get().addOnSuccessListener(snapshots -> {
            if (snapshots.getDocuments().size() > 0) {
                for (int i = 0; i < snapshots.getDocuments().size(); i++) {
                    VentilatorSession session = snapshots.getDocuments().get(i).toObject(VentilatorSession.class);
                    if (session != null)
                        sessions.add(session);
                    if (i == snapshots.getDocuments().size() - 1) {
                        binding.progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "getVentilatorSessions: here");
                        List<Integer> heartRates = Stream.of(sessions).map(VentilatorSession::getHeartRate)
                                .collect(Collectors.toList());

                        List<Float> oxygenPercentages = Stream.of(sessions).map(VentilatorSession::getOxygenPercentage)
                                .collect(Collectors.toList());
                        List<Float> temperatures = Stream.of(sessions).map(VentilatorSession::getTemperature)
                                .collect(Collectors.toList());
                        int normal = 0;
                        int asthma = 0;
                        int pneumonia = 0;
                        int covid_19 = 0;
                        for (int j = 0; j < diseases.size(); j++) {
                            heartRates.add((int) diseases.get(j).getHeartRate().get("average"));
                            oxygenPercentages.add((float) diseases.get(j).getOxygen().get("average"));
                            temperatures.add((float) diseases.get(j).getTemperature().get("average"));
                        }
                        for (int k = 0; k < heartRates.size(); k++) {
                            ArrayList<Float> c1_normal = new ArrayList<>();
                            ArrayList<Float> c2_asthma = new ArrayList<>();
                            ArrayList<Float> c3_pneumonia = new ArrayList<>();
                            ArrayList<Float> c4_covid_19 = new ArrayList<>();

                            for (int x = 0; x < diseases.size(); x++) {
                                int averageMHeartRate = heartRates.get(k) - heartRates.get(x);
                                float averageMOxygen = oxygenPercentages.get(k) - oxygenPercentages.get(x);
                                float averageMTemperature = temperatures.get(k) - temperatures.get(x);
                                float manhattanDistancMe = Math.abs(averageMHeartRate) + Math.abs(averageMOxygen) + Math.abs(averageMTemperature);
                                if (x == 0)
                                    c1_normal.add(manhattanDistancMe);
                                else if (x == 1)
                                    c2_asthma.add(manhattanDistancMe);
                                else if (x == 2)
                                    c3_pneumonia.add(manhattanDistancMe);
                                else if (x == 3)
                                    c4_covid_19.add(manhattanDistancMe);
                            }
                            int p=0;
                             while (p < c4_covid_19.size()) {
                                if (c1_normal.get(p) <= c2_asthma.get(p) && c1_normal.get(p) <= c3_pneumonia.get(p) && c1_normal.get(p) <= c4_covid_19.get(p)) {
                                    normal++;
                                    p++;
                                    continue;

                                } else if (c2_asthma.get(p) <= c1_normal.get(p) && c2_asthma.get(p) <= c3_pneumonia.get(p) && c2_asthma.get(p) <= c4_covid_19.get(p)) {
                                    asthma++;
                                    p++;
                                    continue;
                                } else if (c3_pneumonia.get(p) <= c1_normal.get(p) && c3_pneumonia.get(p) <= c2_asthma.get(p) && c3_pneumonia.get(p) <= c4_covid_19.get(p)) {
                                    pneumonia++;
                                    p++;
                                    continue;
                                } else if (c4_covid_19.get(p) <= c1_normal.get(p) && c4_covid_19.get(p) <= c2_asthma.get(p) && c4_covid_19.get(p) <= c3_pneumonia.get(p)) {
                                    covid_19++;
                                    p++;
                                    continue;
                                }

                            }
                        }
                        Log.e(TAG, "getVentilatorSessions: normal:\t " + normal + "\nasthma:\t" + asthma + "\npneumonia:\t" + pneumonia + "\ncovid-19:\t" + covid_19);
                        int TNOP=normal+asthma+pneumonia+covid_19;
                        float normalC=normal/TNOP*100;
                        float asthmaC=asthma/TNOP*100;
                        float pneumoniaC=pneumonia/TNOP*100;
                        float covid_19C=covid_19/TNOP*100;
                        setData(sessions.size());

                    }
                }
            }
        });
    }

    private void getDiseases() {
        HashMap<String, Object> heartRate = new HashMap<>();
        heartRate.put("low", 60);
        heartRate.put("high", 100);
        heartRate.put("average", (60 + 100) / 2);
        HashMap<String, Object> oxygen = new HashMap<>();
        oxygen.put("low", 0.9f);
        oxygen.put("high", 1.0f);
        oxygen.put("average", (0.9f + 1.0f) / 2);
        HashMap<String, Object> temperature = new HashMap<>();
        temperature.put("low", 0.361f);
        temperature.put("high", 0.372f);
        temperature.put("average", (0.361f + 0.372f) / 2);
        diseases.add(new Disease(
                "Normal",
                heartRate,
                oxygen,
                temperature
        ));

        HashMap<String, Object> asthmaHeartRate = new HashMap<>();
        asthmaHeartRate.put("low", 120);
        asthmaHeartRate.put("high", 150);
        asthmaHeartRate.put("average", (120 + 150) / 2);
        HashMap<String, Object> asthmaOxygen = new HashMap<>();
        asthmaOxygen.put("low", 0.95f);
        asthmaOxygen.put("high", 1.0f);
        asthmaOxygen.put("average", (0.95f + 1.0f) / 2);
        HashMap<String, Object> asthmaTemperature = new HashMap<>();
        asthmaTemperature.put("low", 0.2f);
        asthmaTemperature.put("high", 0.216f);
        asthmaTemperature.put("average", (0.2f + 0.216f) / 2);
        diseases.add(new Disease(
                "Asthma",
                asthmaHeartRate,
                asthmaOxygen,
                asthmaTemperature
        ));

        HashMap<String, Object> pneumoniaHeartRate = new HashMap<>();
        pneumoniaHeartRate.put("low", 150);
        pneumoniaHeartRate.put("high", 180);
        pneumoniaHeartRate.put("average", (150 + 180) / 2);
        HashMap<String, Object> pneumoniaOxygen = new HashMap<>();
        pneumoniaOxygen.put("low", 0.91f);
        pneumoniaOxygen.put("high", 0.94f);
        pneumoniaOxygen.put("average", (0.91f + 0.94f) / 2);
        HashMap<String, Object> pneumoniaTemperature = new HashMap<>();
        pneumoniaTemperature.put("low", 0.4f);
        pneumoniaTemperature.put("high", 0.405f);
        pneumoniaTemperature.put("average", (0.4f + 0.405f) / 2);
        diseases.add(new Disease(
                "Pneumonia",
                pneumoniaHeartRate,
                pneumoniaOxygen,
                pneumoniaTemperature
        ));

        HashMap<String, Object> covid19HeartRate = new HashMap<>();
        covid19HeartRate.put("low", 100);
        covid19HeartRate.put("high", 120);
        covid19HeartRate.put("average", (100 + 120) / 2);
        HashMap<String, Object> covid19Oxygen = new HashMap<>();
        covid19Oxygen.put("low", 0.88f);
        covid19Oxygen.put("high", 0.92f);
        covid19Oxygen.put("average", (0.88f + 0.92f) / 2);
        HashMap<String, Object> covid19Temperature = new HashMap<>();
        covid19Temperature.put("low", 0.378f);
        covid19Temperature.put("high", 0.392f);
        covid19Temperature.put("average", (0.378f + 0.392f) / 2);
        diseases.add(new Disease(
                "Covid-19",
                covid19HeartRate,
                covid19Oxygen,
                covid19Temperature
        ));
    }

    private void setData(float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (range == 0) {
            for (int i = 0; i < diseases.size(); i++) {
                entries.add(new PieEntry(/*(float) ((Math.random() * range) + range / 5)*/ diseases.get(i).getTotalAverage(),
                        diseases.get(i).getName()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Distribution results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);


        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tfLight);
        binding.pie.setData(data);
        binding.pie.spin(1000, binding.pie.getRotationAngle(), binding.pie.getRotationAngle() + 360, Easing.EaseInOutCubic);
        // undo all highlights
        binding.pie.highlightValues(null);

        binding.pie.invalidate();
    }

}