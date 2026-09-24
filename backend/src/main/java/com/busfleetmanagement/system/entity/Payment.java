package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
@Access(AccessType.FIELD)
public class Payment {
    @Id
    @Column(name = "PaymentID", nullable = false, unique = true)
    private int PaymentID;

    @ManyToOne
    @JoinColumn(name = "BookingID", referencedColumnName = "BookingID")
    private Booking booking;

    @Column(name = "Amount", nullable = false)
    private BigDecimal Amount;

    @Column(name = "PaymentDate", nullable = false)
    private LocalDateTime PaymentDate;

    @Column(name = "PaymentMethod", nullable = false, length = 50)
    private String PaymentMethod;

    @Column(name = "PaymentStatus", nullable = false)
    private String PaymentStatus;

    @Column(name = "TransactionReference", nullable = false, length = 100)
    private String TransactionReference;

    public Payment(){
    }

    public int getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(int paymentID) {
        PaymentID = paymentID;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getTransactionReference() {
        return TransactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        TransactionReference = transactionReference;
    }
}
