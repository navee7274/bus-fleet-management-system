package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, String> {
}