package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
