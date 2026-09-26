package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;
import jakarta.servlet.Registration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Journey")
@Access(AccessType.FIELD)
public class Journey {

    @Id
    @Column(name = "JourneyID", nullable = false, unique = true, length = 20)
    private int journeyID;

    @Column(name = "JourneyDate", nullable = false)
    private LocalDate journeyDate;

    @ManyToOne
    @JoinColumn(name = "BRegistrationNo", referencedColumnName = "BRegistrationNo")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "DriverID", referencedColumnName = "DriverID")
    private Driver driver;

    @Column(name = "Purpose", nullable = true, unique = false, length = 100)
    private String purpose;

    @Column(name = "ClientDestination", nullable = false, unique = false, length = 20)
    private String clientDestination;

    @Column(name = "StartOdometer", nullable = false, unique = true, length = 20)
    private BigDecimal startOdometer;

    @Column(name = "EndOdometer", nullable = false, unique = true, length = 20)
    private BigDecimal endOdometer;

    @Column(name = "KMTraveled", nullable = false, unique = true, length = 10)
    private BigDecimal kmTraveled;

    @Column(name = "IncomeAmount", nullable = false, unique = false, length = 10)
    private BigDecimal incomeAmount;

    @Column(name = "Notes", nullable = true, unique = false, length =100)
    private String notes;

    public Journey() {
    }

    public int getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getClientDestination() {
        return clientDestination;
    }

    public void setClientDestination(String clientDestination) {
        this.clientDestination = clientDestination;
    }

    public BigDecimal getStartOdometer() {
        return startOdometer;
    }

    public void setStartOdometer(BigDecimal startOdometer) {
        this.startOdometer = startOdometer;
    }

    public BigDecimal getEndOdometer() {
        return endOdometer;
    }

    public void setEndOdometer(BigDecimal endOdometer) {
        this.endOdometer = endOdometer;
    }

    public BigDecimal getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(BigDecimal kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
