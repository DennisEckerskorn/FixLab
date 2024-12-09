package com.app.fixlab.models.repair;

import com.app.fixlab.models.devices.DeviceStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code Diagnosis} class represents the diagnosis process for a device during a repair.
 * It includes information about the diagnosis, such as its description, estimated cost, estimated time,
 * and the status of the diagnosis. Additionally, it maintains a checklist of various tests or actions
 * performed during the diagnosis, allowing tracking of which items have been completed.
 *
 * <p>The checklist contains items that need to be tested or inspected during the diagnosis,
 * such as memory, hard drive, battery, malware scan, etc. Each of these items can be marked as completed.</p>
 *
 * <p>This class implements {@link Serializable} to allow the diagnosis object to be serialized and restored.</p>
 *
 * @author [Dennis Eckerskorn]
 * @see DeviceStatus
 * @see DiagnosisCheckItem
 */
public class Diagnosis implements Serializable {
    private String description;
    private String estimatedCost;
    private String estimatedTime;
    private final Date diagnosisDate;
    private DeviceStatus status;
    private Map<DiagnosisCheckItem, Boolean> checkList;

    /**
     * Enum representing the different items in the diagnosis checklist.
     * These items represent tests or actions that need to be completed during the diagnosis.
     */
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

    /**
     * Default constructor initializing a new diagnosis with default values.
     * Initializes the checklist with all items marked as incomplete (false).
     */
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
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

    /**
     * Initializes the checklist with all items marked as incomplete (false).
     */
    private void initializeCheckList() {
        checkList = new HashMap<>();
        for (DiagnosisCheckItem item : DiagnosisCheckItem.values()) {
            checkList.put(item, false);
        }
    }

    /**
     * Marks a specific checklist item as completed or not completed.
     *
     * @param item    The checklist item to be marked.
     * @param checked {@code true} if the item is completed, {@code false} otherwise.
     */
    public void setCheckItem(DiagnosisCheckItem item, boolean checked) {
        checkList.put(item, checked);
    }

    /**
     * Gets the completion status of a specific checklist item.
     *
     * @param item The checklist item to check.
     * @return {@code true} if the item is completed, {@code false} otherwise.
     */
    public boolean getCheckItem(DiagnosisCheckItem item) {
        return Boolean.TRUE.equals(checkList.getOrDefault(item, false));
    }

    public Map<DiagnosisCheckItem, Boolean> getCheckList() {
        return checkList;
    }

    /**
     * Gets a list of completed checklist items.
     *
     * @return A list of {@link DiagnosisCheckItem} that are marked as completed.
     */
    public List<DiagnosisCheckItem> getCompletedCheckItems() {
        List<DiagnosisCheckItem> completedItems = new ArrayList<>();
        for (Map.Entry<DiagnosisCheckItem, Boolean> entry : checkList.entrySet()) {
            if (entry.getValue()) {
                completedItems.add(entry.getKey());
            }
        }
        return completedItems;
    }

    /**
     * Checks if all checklist items have been completed.
     *
     * @return {@code true} if all checklist items are completed, {@code false} otherwise.
     */
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
