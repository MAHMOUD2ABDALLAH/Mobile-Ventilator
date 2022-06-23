package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.model.Doctor;
import com.example.myapplication.databinding.ActivityRegisterationBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSubmit.setOnClickListener(view -> {
            if (validation()) {
                registerDoctorToFirestore();
            }
        });
    }

    private boolean validation() {
        boolean valid = true;
        if (binding.etFullName.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.name) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.tiePassword.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.password) + " is empty", Toast.LENGTH_SHORT).show();
        }
//        else if (binding.etEmail.getText().toString().isEmpty()){
//            valid=false;
//            Toast.makeText(this, getString(R.string.e_mail_preffix_of_gmail)+" is empty", Toast.LENGTH_SHORT).show();
//        }
        else if (binding.etOrganization.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.organization) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.etOrganizationID.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.organization_id) + " is empty", Toast.LENGTH_SHORT).show();
        }else if (binding.etWorkType.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.work_type) + " is empty", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void registerDoctorToFirestore() {
        RadioButton rbGender = findViewById(binding.rgGender.getCheckedRadioButtonId());
        Doctor newDoctor = new Doctor(
                binding.etFullName.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.tiePassword.getText().toString(),
                rbGender.getText().toString(),
                binding.etOrganization.getText().toString(),
                binding.etOrganizationID.getText().toString(),
                binding.etPhone.getText().toString(),
                binding.etWorkType.getText().toString()
        );
        FirebaseFirestore.getInstance()
                .collection("Doctors")
                .add(newDoctor).addOnSuccessListener(documentReference -> {
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Doctor resister successfully", Toast.LENGTH_SHORT).show();
                });
    }
}