package com.example.parking.service;

import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.entity.VehicleType;
import com.example.parking.exception.InvalidVehicleNumberException;
import com.example.parking.exception.ParkingFullException;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLot implements Parking {

    private static ParkingLot parkingLot;

    private final List<Slot> carParkingSlots;

    private ParkingLot() {
        this.carParkingSlots = new ArrayList<>();
    }

    public static ParkingLot getParkingLot() {
        if (parkingLot == null)
            parkingLot = new ParkingLot();

        return parkingLot;
    }

    public static void clearAll() {
        parkingLot = null;
    }

    public boolean initializeParkingSlots(int numberOfCarParkingSlots) {

        for (int i = 1; i <= numberOfCarParkingSlots; i++) {
            carParkingSlots.add(new Slot(i));
        }

        System.out.printf("Created a car parking lot with %s slots %n", numberOfCarParkingSlots);
        return true;
    }

    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException {

        Slot nextAvailableSlot=getNextAvailableCarParkingSlot();
        nextAvailableSlot.occupySlot(vehicle);


        System.out.printf("Allocated slot number: %d \n", nextAvailableSlot.getSlotNumber());

        return new Ticket(nextAvailableSlot.getSlotNumber(), vehicle.getVehicleNumber(), VehicleType.CAR, LocalDateTime.now());
    }

    @Override
    public int unPark(Ticket ticket) throws InvalidVehicleNumberException {

        int vacatedSlot=0;
        try {
            int charges = 0;
            Slot unParkedSlot = getSlotByVehicleNumber(ticket.getVehicleNumber());
            vacatedSlot=unParkedSlot.getSlotNumber();
            unParkedSlot.vacateSlot();
        } catch (InvalidVehicleNumberException invalidVehicleNumber) {
            System.out.println(invalidVehicleNumber.getMessage());
            throw invalidVehicleNumber;
        }
        return vacatedSlot;
    }

    private Slot getSlotByVehicleNumber(String vehicleNumber) throws InvalidVehicleNumberException {
        for (Slot slot : carParkingSlots) {
            Vehicle vehicle = slot.getParkVehicle();
            if (vehicle != null && vehicle.getVehicleNumber().equals(vehicleNumber)) {
                return slot;
            }
        }
        throw new InvalidVehicleNumberException(
                "Four wheeler with registration number " + vehicleNumber + " not found");
    }

    private Slot getNextAvailableCarParkingSlot() throws ParkingFullException {
        for (Slot slot : carParkingSlots) {
            if (slot.isEmpty()) {
                return slot;
            }
        }
        throw new ParkingFullException("No Empty Slot available");
    }

    private int getParkingTime(Date startDate, Date endDate) {
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        int hours = (int) (secs / 3600);
        return hours;

    }


}