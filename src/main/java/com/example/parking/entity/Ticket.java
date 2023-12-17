package com.example.parking.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @Column(name = "slot_id")
    private int slotNumber;
    @MapsId
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @Column(name = "date")
    private LocalDateTime localDateTime;
    @Embedded
    private Vehicle vehicle;


    public Ticket() {
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
