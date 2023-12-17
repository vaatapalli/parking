package com.example.parking.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Invoice {

    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "vehicle_type")
    private String vehicleType;
    @Column(name = "charges")
    private String charges;
    @Column(name = "hours")
    private Double noOfHours;
    @Column(name = "vacated_slot_Number")
    private int vacatedSlotNumber;
    @Column(name = "entry_time")
    private LocalDateTime entryTime;
    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    public Invoice() {
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public Double getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(Double noOfHours) {
        this.noOfHours = noOfHours;
    }

    public int getVacatedSlotNumber() {
        return vacatedSlotNumber;
    }

    public void setVacatedSlotNumber(int vacatedSlotNumber) {
        this.vacatedSlotNumber = vacatedSlotNumber;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", charges='" + charges + '\'' +
                ", noOfHours=" + noOfHours +
                ", vacatedSlotNumber=" + vacatedSlotNumber +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                '}';
    }
}
