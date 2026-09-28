package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JourneyRepository extends JpaRepository<Journey,Integer> {
    List<Journey>
    findByBusAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Bus bus,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime
    );

    List<Journey>
    findByDriverAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Driver driver,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime
    );
}

