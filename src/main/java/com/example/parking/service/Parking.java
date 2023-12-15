package com.example.parking.service;


import com.example.parking.exception.InvalidVehicleNumberException;
import com.example.parking.exception.ParkingFullException;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
public interface Parking {
	public Ticket park(Vehicle vehicle) throws ParkingFullException;
	public int unPark(Ticket ticket) throws InvalidVehicleNumberException;
}