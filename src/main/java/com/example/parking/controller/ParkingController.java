package com.example.parking.controller;

import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.exception.InvalidSlotNumberException;
import com.example.parking.exception.ParkingFullException;
import com.example.parking.service.ParkingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingController {
    @Autowired
    private ParkingServiceImpl parkingService;
    @GetMapping("/parking-slots")
    public List<Slot> getSlotsStatus()
    {
       return parkingService.getAllSlots();
    }
    @PostMapping(value = "/park-vehicle")
    public Ticket createTicket(@Valid @RequestBody Vehicle vehicle) throws ParkingFullException {
        return parkingService.park(vehicle);
    }
    @PutMapping(value = "/un-park-vehicle/{slot-number}")
    public String createTicket(@PathVariable int slotNumber) throws InvalidSlotNumberException {
        return parkingService.unPark(slotNumber);
    }
}
