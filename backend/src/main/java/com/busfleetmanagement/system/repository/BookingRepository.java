package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Booking;
import com.busfleetmanagement.system.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByStatus(BookingStatus status);
}
