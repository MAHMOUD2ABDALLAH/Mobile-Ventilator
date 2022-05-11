package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registeration extends AppCompatActivity {

    private com.example.myapplication.databinding.ActivityRegisterationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.example.myapplication.databinding.ActivityRegisterationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button5.setOnClickListener(view -> {
            if (validation()){
                Log.e("TAG", "onClick: " );
            }
        });
    }

    private boolean validation(){
        boolean valid=true;
        if (binding.etFullName.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, getString(R.string.name)+" is empty", Toast.LENGTH_SHORT).show();
        }else  if (binding.etPassword.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, getString(R.string.password)+" is empty", Toast.LENGTH_SHORT).show();
        }
//        else if (binding.etEmail.getText().toString().isEmpty()){
//            valid=false;
//            Toast.makeText(this, getString(R.string.e_mail_preffix_of_gmail)+" is empty", Toast.LENGTH_SHORT).show();
//        }
        else  if (binding.etOrganization.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, getString(R.string.organization)+" is empty", Toast.LENGTH_SHORT).show();
        }
        else  if (binding.etOrganizationID.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, getString(R.string.organization_id)+" is empty", Toast.LENGTH_SHORT).show();
        } if (binding.etWorkType.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, getString(R.string.work_type)+" is empty", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    public void gotofirstpage(View view) {

        Intent submit=new Intent(this,submission_done.class);
        startActivity(submit);
    }
}