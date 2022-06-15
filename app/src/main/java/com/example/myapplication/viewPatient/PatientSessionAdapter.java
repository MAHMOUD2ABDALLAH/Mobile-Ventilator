package com.example.myapplication.viewPatient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Doctor;
import com.example.myapplication.data.model.Patient;
import com.example.myapplication.data.model.VentilatorSession;

import java.util.ArrayList;

public class PatientSessionAdapter extends RecyclerView.Adapter<PatientSessionAdapter.PatientSessionViewHolder> {

    private Patient patient;
    private ArrayList<VentilatorSession> list;

    public PatientSessionAdapter(Patient patient, ArrayList<VentilatorSession> list) {
        this.patient=patient;
        this.list = list;
    }

    @NonNull
    @Override
    public PatientSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientSessionViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientSessionViewHolder holder, int position) {
        VentilatorSession session=list.get(position);

        holder.tvID.setText(patient.getNationalID());
        holder.tvNAME.setText(patient.getFullName());
        holder.tvTMP.setText(String.valueOf(session.getTemperature()));
        if (session.getSymptoms()!=null){
            holder.tvBreathing_Noisily.setText(String.valueOf(session.getSymptoms().get("Breathing Noisily")));
            holder.tvCoughing_Up_Blood.setText(String.valueOf(session.getSymptoms().get("Coughing Up Blood")));
            holder.tvDifficulty_Breathing.setText(String.valueOf(session.getSymptoms().get("Difficulty Breathing")));
            holder.tvLingering_Chest_Pain.setText(String.valueOf(session.getSymptoms().get("Lingering Chest Pain")));
            holder.tvMucus.setText(String.valueOf(session.getSymptoms().get("Mucus")));
            holder.tvStubborn_Cough.setText(String.valueOf(session.getSymptoms().get("Stubborn Cough")));
        }
        holder.tvILL.setText(String.valueOf(session.getIllness()));
        holder.tvTMP.setText(String.valueOf(session.getTemperature()));
        holder.tvOXI.setText(String.valueOf(session.getOxygenPercentage()));
        holder.tvPBM.setText(String.valueOf(session.getHeartRate()));
        holder.tvDOCTOR.setText(String.valueOf(session.getDoctorName()));
        holder.tvORG_ID.setText(String.valueOf(session.getOrganizationID()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PatientSessionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID;
        private TextView tvNAME;
        private TextView tvBreathing_Noisily;
        private TextView tvCoughing_Up_Blood;
        private TextView tvDifficulty_Breathing;
        private TextView tvLingering_Chest_Pain;
        private TextView tvMucus;
        private TextView tvStubborn_Cough;
        private TextView tvDATE;
        private TextView tvPBM;
        private TextView tvTMP;
        private TextView tvOXI;
        private TextView tvILL;
        private TextView tvDOCTOR;
        private TextView tvORG_ID;

        public PatientSessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNAME=itemView.findViewById(R.id.tvNAME);
            tvTMP=itemView.findViewById(R.id.tvTMP);
            tvID=itemView.findViewById(R.id.tvID);
            tvDATE=itemView.findViewById(R.id.tvDATE);
            tvPBM=itemView.findViewById(R.id.tvPBM);
            tvOXI=itemView.findViewById(R.id.tvOXI);
            tvBreathing_Noisily=itemView.findViewById(R.id.tvBreathing_Noisily);
            tvCoughing_Up_Blood=itemView.findViewById(R.id.tvCoughing_Up_Blood);
            tvDifficulty_Breathing=itemView.findViewById(R.id.tvDifficulty_Breathing);
            tvLingering_Chest_Pain=itemView.findViewById(R.id.tvLingering_Chest_Pain);
            tvMucus=itemView.findViewById(R.id.tvMucus);
            tvStubborn_Cough=itemView.findViewById(R.id.tvStubborn_Cough);
            tvILL=itemView.findViewById(R.id.tvILL);
            tvDOCTOR=itemView.findViewById(R.id.tvDOCTOR);
            tvORG_ID=itemView.findViewById(R.id.tvORG_ID);
        }
    }
}
