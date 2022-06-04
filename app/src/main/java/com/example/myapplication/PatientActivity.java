package com.example.myapplication;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.model.Patient;
import com.example.myapplication.databinding.ActivityPatientBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientActivity extends AppCompatActivity {

    private ActivityPatientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v -> {
            if (validation())
                savePatient();
        });
    }

    private boolean validation() {
        boolean valid = true;
        if (binding.etFullName.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.name) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.etAge.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.age) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.etNationalId.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.national_id) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.etReNationalId.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.re_national_id) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (!binding.etReNationalId.getText().toString().equals(binding.etNationalId.getText().toString())) {
            valid = false;
            Toast.makeText(this, getString(R.string.re_national_id) + " must equal " + getString(R.string.national_id), Toast.LENGTH_SHORT).show();
        } else if (binding.etPhone.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.phone) + " is empty", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void savePatient() {
        RadioButton rbGender = findViewById(binding.rgGender.getCheckedRadioButtonId());
        Patient newPatient = new Patient(
                binding.etFullName.getText().toString(),
                "123456",
                Integer.parseInt(binding.etAge.getText().toString()),
                binding.etNationalId.getText().toString(),
                rbGender.getText().toString()
        );
        FirebaseFirestore.getInstance()
                .collection("Patients")
                .add(newPatient).addOnSuccessListener(documentReference -> {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Patient added successfully", Toast.LENGTH_SHORT).show();
        });
    }
}