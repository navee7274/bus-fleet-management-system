package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);

    @Query("""
    SELECT b FROM Booking b
    WHERE b.driver = :driver
    AND b.StartDateTime < :endDateTime
    AND b.EndDateTime > :startDateTime
""")
    List<Booking> findByDriverAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            @Param("driver") Driver driver,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("startDateTime") LocalDateTime startDateTime
    );
    @Query("""
    SELECT b FROM Booking b
    WHERE b.bus = :bus
    AND b.StartDateTime < :endDateTime
    AND b.EndDateTime > :startDateTime
""")
    List<Booking> findByBusAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            @Param("bus") Bus bus,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("startDateTime") LocalDateTime startDateTime
    );
}
