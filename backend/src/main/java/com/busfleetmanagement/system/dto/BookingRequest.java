package com.busfleetmanagement.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class BookingRequest {

    // =========================
    // Customer Details
    // =========================

    @NotBlank
    @Size(max = 20)
    private String customerFirstName;

    @NotBlank
    @Size(max = 20)
    private String customerLastName;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String customerContactPhone;

    @NotBlank
    @Email
    @Size(max = 70)
    private String customerContactEmail;

    @NotBlank
    @Size(max = 100)
    private String customerAddress;

    @NotBlank
    @Size(max = 50)
    private String customerCity;


    // =========================
    // Booking Details
    // =========================

    @NotNull
    @Future(message = "Start date and time must be in the future")
    private LocalDateTime startDateTime;

    @NotNull
    @Future(message = "End date and time must be in the future")
    private LocalDateTime endDateTime;

    @NotBlank
    @Size(max = 255)
    private String startLocation;

    @NotBlank
    @Size(max = 255)
    private String destination;

    @Min(value = 1, message = "Passenger count must be at least 1")
    private int passengerCount;

    // =========================
    // Bus
    // =========================

    @NotBlank
    @Size(max = 20)
    private String busRegistrationNo;


    // =========================
    // Getters and Setters
    // =========================

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerContactPhone() {
        return customerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        this.customerContactPhone = customerContactPhone;
    }

    public String getCustomerContactEmail() {
        return customerContactEmail;
    }

    public void setCustomerContactEmail(String customerContactEmail) {
        this.customerContactEmail = customerContactEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getBusRegistrationNo() {
        return busRegistrationNo;
    }

    public void setBusRegistrationNo(String busRegistrationNo) {
        this.busRegistrationNo = busRegistrationNo;
    }
}