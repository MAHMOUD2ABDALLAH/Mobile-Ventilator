package com.example.myapplication.deletePatient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.model.Patient;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivityDeletePatientBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DeletePatientActivity extends AppCompatActivity {

    private ActivityDeletePatientBinding binding;
    private ArrayList<VentilatorSession> sessions=new ArrayList<>();
    private PatientSessionAdapter patientSessionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeletePatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSearch.setOnClickListener(view -> getPatientByNationalID(binding.etNationalID.getText().toString()));
    }

    private void getPatientByNationalID(String nationalID){
        FirebaseFirestore.getInstance().collection("Patients")
                .whereEqualTo("nationalID",nationalID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.getDocuments().size()>0){
                for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()) {
                    Patient currentPatient=documentSnapshot.toObject(Patient.class);
                   if (currentPatient!=null){
                       getPatientSessions(currentPatient.getNationalID());
                       patientSessionAdapter =new PatientSessionAdapter(currentPatient,sessions);
                       binding.rvSessions.setLayoutManager(new LinearLayoutManager(this));
                       binding.rvSessions.setAdapter(patientSessionAdapter);
                   }
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),  e.getMessage()+"", Toast.LENGTH_SHORT).show());

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getPatientSessions(String nationalID){
        FirebaseFirestore.getInstance().collection("VentilatorSessions")
                .whereEqualTo("nationalID",nationalID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.getDocuments().size()>0){
                for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()) {
                    VentilatorSession session=documentSnapshot.toObject(VentilatorSession.class);
                    sessions.add(session);
                    patientSessionAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),  e.getMessage()+"", Toast.LENGTH_SHORT).show());

    }
}