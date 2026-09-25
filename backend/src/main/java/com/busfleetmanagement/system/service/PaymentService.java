package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Payment;
import com.busfleetmanagement.system.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    // CREATE PAYMENT RECORD
    public Payment createPaymentRecord(Payment payment){
        return paymentRepository.save(payment);
    }

    // READ ALL PAYMENT RECORDS
    public List<Payment> getAllPaymentRecords(){
        return paymentRepository.findAll();
    }

    // READ ONLY ONE PAYMENT RECORD
    public Optional<Payment> getPaymentRecordById(int PaymentID){
        return paymentRepository.findById(PaymentID);
    }

    // UPDATE PAYMENT RECORD
    public Payment updatePaymentRecord(int PaymentID, Payment payment){
        Payment existingRecord = paymentRepository.findById(PaymentID)
                .orElseThrow(()-> new RuntimeException("Payment record not found"));

        existingRecord.setBooking(payment.getBooking());
        existingRecord.setAmount(payment.getAmount());
        existingRecord.setPaymentDate(payment.getPaymentDate());
        existingRecord.setPaymentMethod(payment.getPaymentMethod());
        existingRecord.setPaymentStatus(payment.getPaymentStatus());
        existingRecord.setTransactionReference(payment.getTransactionReference());

        return paymentRepository.save(payment);
    }

}
