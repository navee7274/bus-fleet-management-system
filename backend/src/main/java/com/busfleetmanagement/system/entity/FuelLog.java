package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FuelLog")
@Access(AccessType.FIELD)

public class FuelLog {
    @Column(name = "FDate", nullable = false, unique = false, length = 10)
    private Date fDate;

    @Column(name = "FPrice", nullable = false, unique = false, length = 5)
    private BigDecimal fPrice;

    @Column(name = "FCost", nullable = false, unique = false, length = 10)
    private BigDecimal fCost;

    @Column(name = "FLitersfilled", nullable = false, unique = false, length = 3)
    private BigDecimal fLitersfilled;

    public FuelLog() {
    }

    public Date getfDate() {
        return fDate;
    }

    public void setfDate(Date fDate) {
        this.fDate = fDate;
    }

    public BigDecimal getfPrice() {
        return fPrice;
    }

    public void setfPrice(BigDecimal fPrice) {
        this.fPrice = fPrice;
    }

    public BigDecimal getfCost() {
        return fCost;
    }

    public void setfCost(BigDecimal fCost) {
        this.fCost = fCost;
    }

    public BigDecimal getfLitersfilled() {
        return fLitersfilled;
    }

    public void setfLitersfilled(BigDecimal fLitersfilled) {
        this.fLitersfilled = fLitersfilled;
    }
}
