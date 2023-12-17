package com.example.parking.service;

import com.example.parking.entity.Invoice;
import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.exception.InvalidSlotNumberException;
import com.example.parking.exception.ParkingFullException;
import com.example.parking.repository.InvoiceRepository;
import com.example.parking.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {
    Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);
    @Value("${hour.rate}")
    private int ratePerHour;
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Slot> getAllSlots() {
        return parkingRepository.findAll();
    }

    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException {

        List<Slot> slots = parkingRepository.findByIsEmptyTrue();
        List<Slot> availableSlots = slots.stream().filter(Slot::isEmpty).sorted(Comparator.comparing(Slot::getSlotNumber)).toList();
        logger.info("Total Available slots size - {}", availableSlots.size());

        if (availableSlots.size() > 0) {
            LocalDateTime localDateTimeNow = LocalDateTime.now();
            Ticket ticket = new Ticket();
            ticket.setLocalDateTime(localDateTimeNow);
            ticket.setVehicle(vehicle);

            Slot pickSlot = availableSlots.stream().findFirst().get();
            pickSlot.setEmpty(false);
            ticket.setSlot(pickSlot);
            pickSlot.setTicket(ticket);

            Slot slot = parkingRepository.save(pickSlot);
            logger.info("Slot number {} allocated successfully", slot.getSlotNumber());
            return ticket;
        }
        throw new ParkingFullException("Sorry, Parking slots are full");

    }


    @Override
    @Transactional
    public String unPark(int slotNumber) throws InvalidSlotNumberException {

        Optional<Slot> slotData = parkingRepository.findById(slotNumber);

        if (slotData.isPresent()) {
            Slot slot = slotData.get();
            logger.info("Slot details {} ", slotData.get());

            Ticket ticket = slot.getTicket();

            if (ticket != null) {
                LocalDateTime localDateTimeNow = LocalDateTime.now();
                double hours = Duration.between(ticket.getLocalDateTime(), localDateTimeNow).toHours();
                double charges = ratePerHour * hours;
                logger.info("Today per hour rate - £{}", slot.getSlotNumber());

                slot.setEmpty(true);
                slot.setTicket(null);

                parkingRepository.save(slot);
                logger.info("Slot number {} de-allocated", slot.getSlotNumber());

                Invoice invoice = new Invoice();
                String charge = "£" + charges;
                invoice.setCharges(charge);
                invoice.setEntryTime(ticket.getLocalDateTime());
                invoice.setVehicleNumber(ticket.getVehicle().getVehicleNumber());
                invoice.setVehicleType(ticket.getVehicle().getVehicleType());
                invoice.setVacatedSlotNumber(slot.getSlotNumber());
                invoice.setNoOfHours(hours);
                invoice.setExitTime(localDateTimeNow);

                Invoice invoiceData = invoiceRepository.save(invoice);
                logger.info("Invoice data saved successfully - {} ", invoiceData);

                return charge;
            }
        }
        throw new InvalidSlotNumberException("Invalid Slot Number");

    }

}
