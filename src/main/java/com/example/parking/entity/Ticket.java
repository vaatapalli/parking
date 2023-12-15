package com.example.parking.entity;

import java.time.LocalDateTime;

public class Ticket {
    private int slotNumber;
    private String vehicleNumber;
    private LocalDateTime localDateTime;
    private VehicleType vehicleType;

    public Ticket(int slotNumber, String vehicleNumber, VehicleType vehicleType, LocalDateTime localDateTime) {
        super();
        this.slotNumber = slotNumber;
        this.vehicleNumber = vehicleNumber;
        this.localDateTime = localDateTime;
        this.setVehicleType(vehicleType);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Ticket [slotNumber=" + slotNumber + ", vehicleNumber=" + vehicleNumber + ", localDateTime=" + localDateTime
                + ", vehicleType=" + vehicleType + "]";
    }

}
