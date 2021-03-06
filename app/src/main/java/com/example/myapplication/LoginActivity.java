package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.SharedPrefManager;
import com.example.myapplication.data.model.Doctor;
import com.example.myapplication.databinding.ActivityLoginPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private SharedPrefManager manager;
    private ActivityLoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager=new SharedPrefManager(this);
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
                    if (queryDocumentSnapshots.getDocuments().size()>0){
                        Doctor doctor = queryDocumentSnapshots.getDocuments().get(0).toObject(Doctor.class);
                        if (doctor!=null){
                            manager.saveDoctor(doctor);
                            startActivity(new Intent(getApplicationContext(), ControlSystemActivity.class));
                            Toast.makeText(getApplicationContext(), "Doctor " + doctor.getFullName() + " logged in successfully", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Doctor not found ", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                  }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),  "username or password is wrong", Toast.LENGTH_SHORT).show());
    }

    private void clearForm() {
        binding.etFullName.setText("");
        binding.etPassword.setText("");
    }
}