package com.example.parking.integrationtest;

import com.example.parking.entity.Slot;
import com.example.parking.entity.Ticket;
import com.example.parking.entity.Vehicle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "");
    }

    @Test
    public void testParkingVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("1234");
        vehicle.setVehicleType("car");
        Ticket ticket = restTemplate.postForObject(baseUrl + "/park-vehicle", vehicle, Ticket.class);
        assertEquals("1234", ticket.getVehicle().getVehicleNumber());
        assertEquals("car", ticket.getVehicle().getVehicleType());
    }

    @Test
    public void testFindProductById() {
        List<Slot> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Slot slot = new Slot();
            slot.setSlotNumber(i);
            slot.setEmpty(true);
            list.add(slot);
        }
        h2Repository.saveAll(list);
        List<Slot> slots = restTemplate.getForObject(baseUrl + "/parking-slots", List.class);
        assertAll(
                () -> assertNotNull(slots),
                () -> assertEquals(5, h2Repository.findAll().size())

        );

    }


}
