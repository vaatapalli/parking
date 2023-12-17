package com.example.parking.controller;

import com.example.parking.entity.Invoice;
import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.exception.InvalidSlotNumberException;
import com.example.parking.exception.ParkingFullException;
import com.example.parking.repository.InvoiceRepository;
import com.example.parking.service.ParkingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ParkingController {
    @Autowired
    private ParkingServiceImpl parkingService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/parking-slots")
    public List<Slot> getSlotsStatus() {
        return parkingService.getAllSlots();
    }

    @GetMapping("/invoice")
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    @GetMapping("/invoice/{invoiceId}")
    public Optional<Invoice> getInvoice(@PathVariable long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }
    @PostMapping(value = "/park-vehicle")
    public Ticket parkVehicle(@Valid @RequestBody Vehicle vehicle) throws ParkingFullException {
        return parkingService.park(vehicle);
    }

    @PutMapping(value = "/un-park-vehicle/{slotNumber}")
    public String unParkVehicle(@PathVariable int slotNumber) throws InvalidSlotNumberException {
        return parkingService.unPark(slotNumber);
    }
}
