package com.example.parking.repository;


import com.example.parking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Slot, Integer> {
    List<Slot> findByIsEmptyTrue();
}
