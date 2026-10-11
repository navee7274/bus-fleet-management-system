package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.booking.BookingID = :bookingId")
    Optional<Payment> findPaymentByBookingId(
            @Param("bookingId") int bookingId
    );
}
