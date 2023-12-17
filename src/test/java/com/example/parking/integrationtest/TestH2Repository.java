package com.example.parking.integrationtest;

import com.example.parking.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Slot,Integer> {
}