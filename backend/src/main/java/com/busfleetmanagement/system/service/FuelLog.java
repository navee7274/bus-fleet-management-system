package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "FuelLog")
@Access(AccessType.FIELD)

public class FuelLog {

    @Id
    @Column(name = "FuelLogID", nullable = false, unique = true, length = 20)
    private int FuelLogID;

    @ManyToOne
    @JoinColumn(name = "BRegistrationNo", referencedColumnName = "BRegistrationNo")
    private Bus bus;

    @Column(name = "FDate", nullable = false, unique = false, length = 10)
    private LocalDate fDate;

    @Column(name = "FPrice", nullable = false, unique = false, length = 5)
    private BigDecimal fPrice;

    @Column(name = "FCost", nullable = false, unique = false, length = 10)
    private BigDecimal fCost;

    @Column(name = "FLitersfilled", insertable = false, updatable = false)
    private BigDecimal fLitersfilled;

    public FuelLog() {
    }

    public int getFuelLogID() {
        return FuelLogID;
    }

    public void setFuelLogID(int fuelLogID) {
        FuelLogID = fuelLogID;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public LocalDate getFDate() {
        return fDate;
    }

    public void setFDate(LocalDate fDate) {
        this.fDate = fDate;
    }

    public BigDecimal getFPrice() {
        return fPrice;
    }

    public void setFPrice(BigDecimal fPrice) {
        this.fPrice = fPrice;
    }

    public BigDecimal getFCost() {
        return fCost;
    }

    public void setFCost(BigDecimal fCost) {
        this.fCost = fCost;
    }

    public BigDecimal getFLitersfilled() {
        return fLitersfilled;
    }

    public void setFLitersfilled(BigDecimal fLitersfilled) {
        this.fLitersfilled = fLitersfilled;
    }
}
