package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.model.VentilatorSession;
import com.example.myapplication.databinding.ActivitySymptomsBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SymptomsActivity extends AppCompatActivity {

    private ActivitySymptomsBinding binding;
    private VentilatorSession session;

    private CollectionReference reference;
    private String currentSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySymptomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseFirestore.getInstance().collection("VentilatorSessions");
        currentSessionId = reference.document().getId();

        session=getIntent().getExtras().getParcelable("newSession");

        binding.button14.setOnClickListener(v -> {
            HashMap<String,String> symptoms=new HashMap<>();
            symptoms.put("Difficulty Breathing", ((RadioButton) findViewById(binding.radioGroup2.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Stubborn Cough", ((RadioButton) findViewById(binding.radioGroup3.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Breathing Noisily", ((RadioButton) findViewById(binding.radioGroup5.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Lingering Chest Pain", ((RadioButton) findViewById(binding.radioGroup6.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Mucus", ((RadioButton) findViewById(binding.radioGroup7.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Coughing Up Blood", ((RadioButton) findViewById(binding.radioGroup4.getCheckedRadioButtonId())).getText().toString());
            session.setSymptoms(symptoms);
            reference.document(currentSessionId).set(session);
            startActivity(new Intent(getApplicationContext(),ControlSystemActivity.class));
            finish();
            Toast.makeText(getApplicationContext(), "Session uploaded successfully", Toast.LENGTH_SHORT).show();
        });
    }
}