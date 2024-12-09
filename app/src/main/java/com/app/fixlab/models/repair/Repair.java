package com.app.fixlab.models.repair;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * The {@code Repair} class represents a repair process for a device.
 * It includes details about the technician, device, client, diagnosis, repair status,
 * repair date, and the result of the repair.
 *
 * <p>It also manages the state of the repair, including whether it is pending, diagnosed,
 * in progress, completed, or cancelled.</p>
 *
 * <p>Each repair has a technician assigned, a client for whom the repair is done,
 * and a device being repaired. The diagnosis and the repair result are also part of the repair information.</p>
 *
 * <p>This class implements {@link Serializable} to allow the repair objects to be saved and restored.</p>
 *
 * <p>Repair Statuses:
 * <ul>
 *   <li>{@code PENDING}: The repair is yet to be started.</li>
 *   <li>{@code DIAGNOSED}: The diagnosis has been completed.</li>
 *   <li>{@code IN_PROGRESS}: The repair is actively being worked on.</li>
 *   <li>{@code COMPLETED}: The repair has been finished and closed.</li>
 *   <li>{@code CANCELLED}: The repair has been cancelled.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 * @see Diagnosis
 * @see Technician
 * @see Client
 * @see Device
 */
public class Repair implements Serializable {
    private Person technician;
    private Device device;
    private Client client;
    private Diagnosis diagnosis;
    private RepairStatus status;
    private final Date repairDate;
    private String repairResult;

    /**
     * Enum representing the status of a repair.
     */
    public enum RepairStatus {
        PENDING,
        DIAGNOSED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    /**
     * Constructor to initialize a new repair with a technician, device, and client.
     * The repair starts with the status of {@link RepairStatus#PENDING}.
     *
     * @param technician The technician assigned to the repair.
     * @param device     The device that needs to be repaired.
     * @param client     The client who owns the device.
     */
    public Repair(Person technician, Device device, Client client) {
        this.technician = technician;
        this.device = device;
        this.client = client;
        this.status = RepairStatus.PENDING;
        this.repairDate = new Date();
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
        if (diagnosis != null) {
            this.status = RepairStatus.DIAGNOSED;
        }
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
        if (repairResult != null) {
            this.status = RepairStatus.COMPLETED;
        }
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public Person getTechnician() {
        return technician;
    }

    public Device getDevice() {
        return device;
    }

    public RepairStatus getStatus() {
        return status;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStatus(RepairStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repair repair = (Repair) o;
        return Objects.equals(technician, repair.technician) && Objects.equals(device, repair.device) && status == repair.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(technician, device, status);
    }

    @Override
    public String toString() {
        return "Repair{" +
                "technician=" + technician +
                ", device=" + device +
                ", client=" + client +
                ", diagnosis=" + diagnosis +
                ", status=" + status +
                ", repairDate=" + repairDate +
                ", repairResult='" + repairResult + '\'' +
                '}';
    }
}
