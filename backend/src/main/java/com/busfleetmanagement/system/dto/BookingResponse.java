package com.busfleetmanagement.system.dto;

import com.busfleetmanagement.system.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingResponse {

    private int bookingID;

    // Customer Details
    private String customerFirstName;
    private String customerLastName;
    private String customerContactPhone;
    private String customerContactEmail;
    private String customerAddress;
    private String customerCity;

    // Booking Details
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String startLocation;
    private String destination;

    private int passengerCount;

    // Assigned Resources
    private String busRegistrationNo;
    private String driverID;

    // Pricing
    private BigDecimal estimatedCost;
    private BigDecimal finalPrice;
    private BigDecimal advanceAmount;

    // Status
    private BookingStatus status;

    private LocalDateTime createdAt;

    private Integer journeyID;


    // Booking ID
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }


    // Customer First Name
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }


    // Customer Last Name
    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }


    // Customer Phone
    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }


    // Customer Email
    public String getCustomerContactEmail() {
        return customerContactEmail;
    }

    public void setCustomerContactEmail(String customerContactEmail) {
        this.customerContactEmail = customerContactEmail;
    }


    // Customer Address
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }


    // Customer City
    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }


    // Start Date/Time
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }


    // End Date/Time
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }


    // Start Location
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }


    // Destination
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    // Passenger Count
    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }


    // Bus Registration Number
    public String getBusRegistrationNo() {
        return busRegistrationNo;
    }

    public void setBusRegistrationNo(String busRegistrationNo) {
        this.busRegistrationNo = busRegistrationNo;
    }


    // Driver ID
    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }


    // Estimated Cost
    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }


    // Final Price
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }


    // Advance Amount
    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }


    // Status
    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }


    // Created At
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    // Journey ID
    public Integer getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(Integer journeyID) {
        this.journeyID = journeyID;
    }
}