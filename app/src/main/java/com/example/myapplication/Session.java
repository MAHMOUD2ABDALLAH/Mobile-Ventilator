package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.model.Disease;
import com.example.myapplication.data.model.VentilatorSession;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Session extends AppCompatActivity {
    private static final String TAG = "Session";
    private String currentPatientNationalID;
    private VentilatorSession newSession;
    private ArrayList<Disease> diseases = new  ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        currentPatientNationalID = getIntent().getExtras().getString("nationalID");
        newSession = new VentilatorSession(currentPatientNationalID,
                88, 0.72f, 36.8f);
        getDiseases();
    }

    private void getDiseases() {
        Log.e(TAG, "getDiseases: here" );
        FirebaseFirestore.getInstance().collection("Diseases")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.e(TAG, "getDiseases: hooooo"+queryDocumentSnapshots.getDocuments().size() );
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            Disease disease = snapshot.toObject(Disease.class);
                            Log.e(TAG, "getDiseases: "+disease );
                            if (disease != null) {
                                disease.setName(snapshot.getId());
                                diseases.add(disease);
                                Log.e(TAG, "onSuccess: "+diseases );
                            }
                        }
                    }
                }).addOnFailureListener(e -> Log.e(TAG, "onFailure: "+e ));
    }

}