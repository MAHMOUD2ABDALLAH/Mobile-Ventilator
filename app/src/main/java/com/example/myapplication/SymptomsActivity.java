package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myapplication.databinding.ActivitySymptomsBinding;

import java.util.HashMap;

public class SymptomsActivity extends AppCompatActivity {

    private ActivitySymptomsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySymptomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.button14.setOnClickListener(v -> {
            HashMap<String,String> symptoms=new HashMap<>();
            symptoms.put("Difficulty Breathing", ((RadioButton) findViewById(binding.radioGroup2.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Stubborn Cough", ((RadioButton) findViewById(binding.radioGroup3.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Breathing Noisily", ((RadioButton) findViewById(binding.radioGroup5.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Lingering Chest Pain", ((RadioButton) findViewById(binding.radioGroup6.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Mucus", ((RadioButton) findViewById(binding.radioGroup7.getCheckedRadioButtonId())).getText().toString());
            symptoms.put("Coughing Up Blood", ((RadioButton) findViewById(binding.radioGroup4.getCheckedRadioButtonId())).getText().toString());
            Intent intent=new Intent();
            Bundle extras = new Bundle();
            extras.putSerializable("symptoms",symptoms);
            intent.putExtras(extras);
            setResult(1,intent);
            finish();
        });
    }
}