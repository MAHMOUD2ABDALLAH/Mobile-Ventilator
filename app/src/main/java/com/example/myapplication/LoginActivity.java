package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.model.Doctor;
import com.example.myapplication.databinding.ActivityLoginPageBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static Doctor CURRENT_DOCTOR;
    private ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            if (validFields()) {
                String fullName = Objects.requireNonNull(binding.etFullName.getText()).toString();
                String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
                login(fullName, password);
            }
        });
    }

    private boolean validFields() {
        boolean valid = true;
        if (binding.etFullName.getText() != null && binding.etFullName.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.name) + " is empty", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText() != null && binding.etPassword.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(this, getString(R.string.password) + " is empty", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void login(String userName, String password) {
        FirebaseFirestore.getInstance().collection("Doctors")
                .whereEqualTo("fullName", userName)
                .whereEqualTo("password", password).get().addOnSuccessListener(queryDocumentSnapshots -> {
            CURRENT_DOCTOR = queryDocumentSnapshots.getDocuments().get(0).toObject(Doctor.class);
            startActivity(new Intent(getApplicationContext(), ControlSystemActivity.class));
            Toast.makeText(getApplicationContext(), "Doctor " + CURRENT_DOCTOR.getFullName() + " logged in successfully", Toast.LENGTH_SHORT).show();
        });
    }
}