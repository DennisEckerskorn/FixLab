package com.app.fixlab.listeners;

import com.app.fixlab.models.repair.Diagnosis;

public interface OnCheckedChangeListener {
    void onCheckedChange(Diagnosis.DiagnosisCheckItem item, boolean isChecked);
}
