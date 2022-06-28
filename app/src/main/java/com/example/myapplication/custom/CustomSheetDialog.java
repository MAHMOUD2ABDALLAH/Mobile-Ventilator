package com.example.myapplication.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CustomSheetDialog extends BottomSheetDialogFragment implements CustomAdapter.CustomClickListener {

    private final ArrayList<Integer> customRanges = new ArrayList<>();
    private final CustomSheetDialogListener dialogListener;

    public CustomSheetDialog(CustomSheetDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 10; i <= 100; i+=10) {
            customRanges.add(i);
        }

        RecyclerView rvCustoms = view.findViewById(R.id.rvCustoms);
        CustomAdapter customAdapter = new CustomAdapter(customRanges);
        customAdapter.setCustomClickListener(this);
        rvCustoms.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCustoms.setAdapter(customAdapter);
    }

    @Override
    public void onCustomClicked(Integer custom) {
        dialogListener.onCustomSelected(custom);
        this.dismiss();
    }

    public interface CustomSheetDialogListener {
        void onCustomSelected(int custom);
    }

}