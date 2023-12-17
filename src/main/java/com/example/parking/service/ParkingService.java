package com.example.parking.service;


import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.exception.InvalidSlotNumberException;
import com.example.parking.exception.ParkingFullException;

public interface ParkingService {
	Ticket park(Vehicle vehicle) throws ParkingFullException;
	String unPark(int slotNumber) throws InvalidSlotNumberException;
}