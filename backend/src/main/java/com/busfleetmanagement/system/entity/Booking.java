package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Access(AccessType.FIELD)
public class Booking {
    @Id
    @Column(name = "BookingID", nullable = false, unique = true)
    private int BookingID;

    @Column(name = "CustomerName", nullable = false, length = 20)
    private String CustomerName;

    @Column(name = "CustomerContact",  nullable = false, length = 10)
    private String CustomerContact;

    @Column(name = "StartDateTime",  nullable = false)
    private LocalDateTime StartDateTime;

    @Column(name = "EndDateTime",  nullable = false)
    private LocalDateTime EndDateTime;

    @Column(name = "StartLocation", nullable = false, length = 255)
    private String StartLocation;

    @Column(name = "Destination", nullable = false, length = 255)
    private String Destination;

    @Column(name = "PassengerCount", nullable = false)
    private int PassengerCount;

    @ManyToOne
    @JoinColumn(name = "BRegistrationNo", referencedColumnName = "BRegistrationNo")
    private Bus bus;

    @Column(name = "EstimatedCost", nullable = false)
    private BigDecimal EstimatedCost;

    @Column(name = "FinalPrice", nullable = false)
    private BigDecimal FinalPrice;

    @Column(name = "BookingStatus", nullable = false, length = 30)
    private String Status;

    @Column(name = "CreatedAt",  nullable = false)
    private LocalDateTime CreatedAt;

    public Booking(){
    }


    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String customerContact) {
        CustomerContact = customerContact;
    }

    public LocalDateTime getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        StartDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        EndDateTime = endDateTime;
    }

    public String getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(String startLocation) {
        StartLocation = startLocation;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getPassengerCount() {
        return PassengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        PassengerCount = passengerCount;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public BigDecimal getEstimatedCost() {
        return EstimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        EstimatedCost = estimatedCost;
    }

    public BigDecimal getFinalPrice() {
        return FinalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        FinalPrice = finalPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }
}
