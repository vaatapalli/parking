package com.example.parking;

import com.example.parking.entity.Slot;
import com.example.parking.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ParkingApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(ParkingApplication.class);
    @Value("${parking.slots.number}")
    private int numberOfCarParkingSlots;
    @Autowired
    ParkingRepository parkingRepository;
    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Slot> list = new ArrayList<>();
        for (int i = 1; i <= numberOfCarParkingSlots; i++) {
            Slot slot = new Slot();
            slot.setSlotNumber(i);
            slot.setEmpty(true);
            list.add(slot);
        }
        parkingRepository.saveAll(list);
        logger.info("Total Number of slots inserted - {}", list.size());
    }
}
