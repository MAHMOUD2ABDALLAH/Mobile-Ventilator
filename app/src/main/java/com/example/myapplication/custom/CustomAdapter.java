package com.example.myapplication.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private final ArrayList<Float> customArrayList;
    private CustomClickListener listener;

    public CustomAdapter(ArrayList<Float> customArrayList) {
        this.customArrayList = customArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCustomNumber.setText(String.valueOf(customArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return customArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCustomNumber;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomNumber = itemView.findViewById(R.id.tvCustomNumber);
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onCustomClicked(customArrayList.get(getAdapterPosition()));
                }
            });
        }
    }

    interface CustomClickListener {
        void onCustomClicked(float custom);
    }

    void setCustomClickListener(CustomClickListener listener){
        this.listener=listener;
    }
}
