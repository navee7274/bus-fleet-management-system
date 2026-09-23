package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Bus")
@Access(AccessType.FIELD)
public class Bus {

    @Id
    @Column(name = "BRegistrationNo", nullable = false, unique = true, length = 20)
    private String bRegistrationNo;

    @Column(name = "BPurchaseDate", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "BPurchasePrice", nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "BCapacity", nullable = false)
    private Integer capacity;

    @Column(name = "BNotes", nullable = false, length = 225)
    private String notes;

    @Column(name = "BActive", nullable = false)
    private boolean active;

    public Bus() {
    }

    public String getBusRegistrationNo() {return bRegistrationNo;}

    public void setBusRegistrationNo(String bRegistrationNo) {this.bRegistrationNo = bRegistrationNo;}

    public LocalDate getPurchaseDate() {return purchaseDate;}

    public void setPurchaseDate(LocalDate purchaseDate) {this.purchaseDate = purchaseDate;}

    public BigDecimal getPurchasePrice() {return purchasePrice;}

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getNotes() {return notes;}

    public void setNotes(String notes) {this.notes = notes;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {this.active = active;}


}