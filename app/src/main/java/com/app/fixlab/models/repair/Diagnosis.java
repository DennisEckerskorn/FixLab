package com.app.fixlab.models.repair;

import com.app.fixlab.models.devices.DeviceStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Diagnosis implements Serializable {
    private final String description;
    private final String estimatedCost;
    private final String estimatedTime;
    private final Date diagnosisDate;
    private DeviceStatus status;
    private Map<DiagnosisCheckItem, Boolean> checkList;

    public enum DiagnosisCheckItem {
        MEMORY_TEST,
        HARD_DRIVE_TEST,
        CPU_TEST,
        NETWORK_TEST,
        BATTERY_TEST,
        PERIPHERALS_TEST,
        DISPLAY_TEST,
        SOUND_TEST,
        USB_PORT_TEST,
        PHYSICAL_INSPECTION,
        CLEANING_REQUIRED,
        TEMPERATURE_TEST,
        POWER_SUPPLY_TEST,
        MALWARE_SCAN,
        OS_CHECK,
        OS_UPDATE,
        OS_UPGRADE,
        OS_REBOOT,
        BIOS_CHECK,
        BIOS_UPDATE,
        BIOS_UPGRADE,
        SOFTWARE_CHECK,
        SOFTWARE_UPDATE,
        OTHER
    }

    public Diagnosis() {
        this.description = "";
        this.estimatedCost = "";
        this.estimatedTime = "";
        this.diagnosisDate = new Date();
        this.status = DeviceStatus.PENDING;
        initializeCheckList();
    }

    public String getDescription() {
        return description;
    }

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    private void initializeCheckList() {
        checkList = new HashMap<>();
        for (DiagnosisCheckItem item : DiagnosisCheckItem.values()) {
            checkList.put(item, false);
        }
    }

    public void setCheckItem(DiagnosisCheckItem item, boolean checked) {
        checkList.put(item, checked);
    }

    public boolean getCheckItem(DiagnosisCheckItem item) {
        return Boolean.TRUE.equals(checkList.getOrDefault(item, false));
    }

    public Map<DiagnosisCheckItem, Boolean> getCheckList() {
        return checkList;
    }

    public List<DiagnosisCheckItem> getCompletedCheckItems() {
        List<DiagnosisCheckItem> completedItems = new ArrayList<>();
        for (Map.Entry<DiagnosisCheckItem, Boolean> entry : checkList.entrySet()) {
            if (entry.getValue()) {
                completedItems.add(entry.getKey());
            }
        }
        return completedItems;
    }

    public boolean isAllCheckItemsCompleted() {
        for (Boolean value : checkList.values()) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagnosis diagnosis = (Diagnosis) o;
        return Objects.equals(description, diagnosis.description) && Objects.equals(estimatedCost, diagnosis.estimatedCost) && Objects.equals(estimatedTime, diagnosis.estimatedTime) && Objects.equals(diagnosisDate, diagnosis.diagnosisDate) && status == diagnosis.status && Objects.equals(checkList, diagnosis.checkList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, estimatedCost, estimatedTime, diagnosisDate, status, checkList);
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "description='" + description + '\'' +
                ", estimatedCost='" + estimatedCost + '\'' +
                ", estimatedTime='" + estimatedTime + '\'' +
                ", diagnosisDate=" + diagnosisDate +
                ", status=" + status +
                ", checkList=" + checkList +
                '}';
    }
}
