package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
