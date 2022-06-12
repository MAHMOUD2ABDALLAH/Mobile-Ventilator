package com.example.myapplication.deletePatient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
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

        holder.tvNAME.setText(patient.getFullName());
        holder.tvTMP.setText(String.valueOf(session.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PatientSessionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTMP;
        private TextView tvID;
        private TextView tvDATE;
        private TextView tvPBM;
        private TextView tvNAME;
        public PatientSessionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNAME=itemView.findViewById(R.id.tvNAME);
            tvTMP=itemView.findViewById(R.id.tvTMP);
            tvID=itemView.findViewById(R.id.tvID);
            tvDATE=itemView.findViewById(R.id.tvDATE);
            tvPBM=itemView.findViewById(R.id.tvPBM);
        }
    }
}
