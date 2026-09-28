package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByDriverAndStartDateTimeLessThanAndEndDateTimeGreaterThan(Driver driver, LocalDateTime endDateTime, LocalDateTime startDateTime);
    List<Booking> findByBusAndStartDateTimeLessThanAndEndDateTimeGreaterThan(Bus bus, LocalDateTime endDateTime, LocalDateTime startDateTime);
}
