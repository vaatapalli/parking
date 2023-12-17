package com.example.parking;


import com.example.parking.entity.Invoice;
import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import com.example.parking.exception.InvalidSlotNumberException;
import com.example.parking.exception.ParkingFullException;
import com.example.parking.repository.InvoiceRepository;
import com.example.parking.repository.ParkingRepository;
import com.example.parking.service.ParkingServiceImpl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


import static org.mockito.Mockito.when;

public class ParkingServiceTests {
    @InjectMocks
    ParkingServiceImpl parkingService;
    @Mock
    ParkingRepository parkingRepository;
    @Mock
    InvoiceRepository invoiceRepository;
    private final LocalDateTime entryLocalDateTime=LocalDateTime.of(2020,1,12,9,0);
    private final LocalDateTime existLocalDateTime=LocalDateTime.of(2020,1,12,10,0);

    @BeforeEach
    void setUp() throws Exception
    {
        /*
         *  This is needed for Mockito to be able to instantiate the Mock Objects
         *  and Inject into the ParkingServiceImpl object
         */
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(parkingService, "ratePerHour", 2);
    }

    @Test
    void testParking() throws ParkingFullException {

        Slot slot1=new Slot();
        slot1.setSlotNumber(1);
        slot1.setEmpty(true);

        Vehicle vehicle=new Vehicle();
        vehicle.setVehicleNumber("1234");
        vehicle.setVehicleType("car");

        List<Slot> availableSlots=new ArrayList<>();
        availableSlots.add(slot1);


        Ticket ticket=new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setLocalDateTime(entryLocalDateTime);

        Slot slot=availableSlots.get(0);
        slot.setTicket(ticket);


         try (MockedStatic<LocalDateTime> localDateTimeMockedStatic=Mockito.mockStatic(LocalDateTime.class)) {
             localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(entryLocalDateTime);
             when(parkingRepository.findByIsEmptyTrue()).thenReturn(availableSlots);
             when(parkingRepository.save(Mockito.any(Slot.class))).thenReturn(slot);

             Ticket ticketDetails=parkingService.park(vehicle);

             Assertions.assertEquals(entryLocalDateTime, ticketDetails.getLocalDateTime());
             Assertions.assertEquals("1234",ticketDetails.getVehicle().getVehicleNumber());
             Assertions.assertEquals("car",ticketDetails.getVehicle().getVehicleType());
             Assertions.assertEquals(1,ticketDetails.getSlot().getSlotNumber());
        }

    }
    @Test
    public void unParkTest() throws InvalidSlotNumberException {

        Vehicle vehicle=new Vehicle();
        vehicle.setVehicleNumber("1234");
        vehicle.setVehicleType("car");

        Ticket ticket=new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setLocalDateTime(entryLocalDateTime);

        Slot slot=new Slot();
        slot.setSlotNumber(1);
        slot.setEmpty(false);
        slot.setTicket(ticket);

        Invoice invoice=new Invoice();
        invoice.setVacatedSlotNumber(1);
        invoice.setVehicleType("car");
        invoice.setVehicleNumber("1234");
        invoice.setEntryTime(entryLocalDateTime);


        Optional<Slot> slotData =Optional.of(slot);
        Duration duration=Duration.between(entryLocalDateTime,existLocalDateTime);

        try(MockedStatic<LocalDateTime> localDateTimeMockedStatic=Mockito.mockStatic(LocalDateTime.class);
            MockedStatic<Duration> durationMockedStatic=Mockito.mockStatic(Duration.class))
        {

            localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(existLocalDateTime);
            durationMockedStatic.when(() -> Duration.between(Mockito.any(LocalDateTime.class),Mockito.any(LocalDateTime.class))).thenReturn(duration);

            when(parkingRepository.findById(Mockito.anyInt())).thenReturn(slotData);
            when(parkingRepository.save(Mockito.any(Slot.class))).thenReturn(slot);
            when(invoiceRepository.save(Mockito.any(Invoice.class))).thenReturn(invoice);

            Assertions.assertEquals("£2.0",parkingService.unPark(1));

        }

    }


}
