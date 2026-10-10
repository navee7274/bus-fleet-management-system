package com.busfleetmanagement.system.entity;

import com.busfleetmanagement.system.enums.BookingStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Access(AccessType.FIELD)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID", nullable = false, unique = true)
    private int BookingID;

    @Column(name = "CustomerFirstName", nullable = false, length = 20)
    private String CustomerFirstName;

    @Column(name = "CustomerLastName", nullable = false, length = 20)
    private String CustomerLastName;

    @Column(name = "CustomerContactPhone",  nullable = false, length = 10)
    private String CustomerContactPhone;

    @Column(name = "CustomerContactEmail",  nullable = false, length = 70)
    private String CustomerContactEmail;

    @Column(name = "CustomerAddress",  nullable = false, length = 100)
    private String CustomerAddress;

    @Column(name = "CustomerCity",  nullable = false, length = 50)
    private String CustomerCity;

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

    @ManyToOne
    @JoinColumn(name = "DriverID", referencedColumnName = "DriverID")
    private Driver driver;

    @Column(name = "EstimatedCost")
    private BigDecimal EstimatedCost;

    @Column(name = "FinalPrice")
    private BigDecimal FinalPrice;

    @Column(name = "AdvanceAmount")
    private BigDecimal AdvanceAmount = BigDecimal.valueOf(8000.00);

    @Enumerated(EnumType.STRING)
    @Column(name = "BookingStatus", nullable = false, length = 30)
    private BookingStatus Status;

    @Column(name = "CreatedAt",  nullable = false)
    private LocalDateTime CreatedAt;

    @OneToOne
    @JoinColumn(name = "JourneyID", referencedColumnName = "JourneyID")
    private Journey journey;

    @PrePersist
    protected void onCreate() {
        CreatedAt = LocalDateTime.now();

        if (Status == null) {
            Status = BookingStatus.PENDING;
        }

        if (AdvanceAmount == null) {
            AdvanceAmount = BigDecimal.valueOf(8000.00);
        }
    }

    public Booking(){
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
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

    public BigDecimal getFinalPrice() {return FinalPrice;}

    public void setFinalPrice(BigDecimal finalPrice) {
        FinalPrice = finalPrice;
    }

    public BookingStatus getStatus() {
        return Status;
    }

    public void setStatus(BookingStatus status) {
        Status = status;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public String getCustomerFirstName() {
        return CustomerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        CustomerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return CustomerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        CustomerLastName = customerLastName;
    }

    public String getCustomerContactPhone() {
        return CustomerContactPhone;
    }

    public void setCustomerContactPhone(String customerContactPhone) {
        CustomerContactPhone = customerContactPhone;
    }

    public String getCustomerContactEmail() {
        return CustomerContactEmail;
    }

    public void setCustomerContactEmail(String customerContactEmail) {
        CustomerContactEmail = customerContactEmail;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return CustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        CustomerCity = customerCity;
    }

    public BigDecimal getAdvanceAmount() {
        return AdvanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        AdvanceAmount = advanceAmount;
    }
}
