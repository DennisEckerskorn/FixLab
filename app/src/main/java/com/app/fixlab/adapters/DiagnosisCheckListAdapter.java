package com.app.fixlab.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiagnosisCheckListAdapter extends RecyclerView.Adapter<DiagnosisCheckListAdapter.DiagnosisCheckListViewHolder> {



    @NonNull
    @Override
    public DiagnosisCheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisCheckListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DiagnosisCheckListViewHolder extends RecyclerView.ViewHolder {

        public DiagnosisCheckListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
