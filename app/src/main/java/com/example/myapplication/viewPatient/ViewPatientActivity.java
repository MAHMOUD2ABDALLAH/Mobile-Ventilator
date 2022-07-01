package com.example.myapplication.viewPatient;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.model.Patient;
import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivityViewPatientBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

public class ViewPatientActivity extends AppCompatActivity {

    private ActivityViewPatientBinding binding;
    private final ArrayList<VentilatorSession> sessions = new ArrayList<>();
    private PatientSessionAdapter patientSessionAdapter;
    private PdfWriterManager pdfWriterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSearch.setOnClickListener(view -> getPatientByNationalID(binding.etNationalID.getText().toString()));
        binding.btnDelete.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etNationalID.getText()))
                Toast.makeText(this, "Enter National ID", Toast.LENGTH_SHORT).show();
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Patient")
                        .setMessage("Are you sure you want to delete this patient?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Continue with delete operation
                            deletePatientData(binding.etNationalID.getText().toString());
                        })
                        .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        binding.btnPrint.setOnClickListener(v -> {
            if (sessions.isEmpty())
                Toast.makeText(this, "No data to print", Toast.LENGTH_SHORT).show();
            else printSessions();
        });
    }

    private void printSessions() {
        String filePath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        pdfWriterManager=new PdfWriterManager(new File(filePath,"sessions.pdf"),sessions);
        pdfWriterManager.saveSessionsAsPdf(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deletePatientData(String nationalID) {
        FirebaseFirestore.getInstance().collection("Patients")
                .whereEqualTo("nationalID", nationalID)
                .get().addOnSuccessListener(snapshots -> {
            if (snapshots.getDocuments().size() > 0) {
                for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                    snapshot.getReference().delete();
                }
            }
        });
        FirebaseFirestore.getInstance().collection("VentilatorSessions")
                .whereEqualTo("nationalID", nationalID)
                .get().addOnSuccessListener(snapshots -> {
            if (snapshots.getDocuments().size() > 0) {
                for (int i = 0; i < snapshots.getDocuments().size(); i++) {
                    snapshots.getDocuments().get(i).getReference().delete();
                    if (i == snapshots.getDocuments().size() - 1) {
                        Toast.makeText(this, "Patient deleted", Toast.LENGTH_SHORT).show();
                        sessions.clear();
                        patientSessionAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void getPatientByNationalID(String nationalID) {
        FirebaseFirestore.getInstance().collection("Patients")
                .whereEqualTo("nationalID", nationalID)
                .get().addOnSuccessListener(patientSnapshots -> {
            if (patientSnapshots.getDocuments().size() > 0) {
                for (DocumentSnapshot documentSnapshot : patientSnapshots.getDocuments()) {
                    Patient currentPatient = documentSnapshot.toObject(Patient.class);
                    if (currentPatient != null) {
                        getPatientSessions(currentPatient.getNationalID());
                        patientSessionAdapter = new PatientSessionAdapter(currentPatient, sessions);
                        binding.rvSessions.setLayoutManager(new LinearLayoutManager(this));
                        binding.rvSessions.setAdapter(patientSessionAdapter);
                    }
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show());

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getPatientSessions(String nationalID) {
        FirebaseFirestore.getInstance().collection("VentilatorSessions")
                .whereEqualTo("nationalID", nationalID)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.getDocuments().size() > 0) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    VentilatorSession session = documentSnapshot.toObject(VentilatorSession.class);
                    sessions.add(session);
                    patientSessionAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show());
    }
}