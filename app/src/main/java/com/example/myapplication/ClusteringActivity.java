package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivityClusteringBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ClusteringActivity extends AppCompatActivity {
    private static final String TAG = "ClusteringActivity";
    private ActivityClusteringBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClusteringBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                        Log.e(TAG, "getVentilatorSessions: heartRates:" + heartRates + " oxygenPercentages: " + oxygenPercentages + " temperatures: " + temperatures);
                    }
                }
            }
        });
    }
}