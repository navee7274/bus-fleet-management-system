package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MaintenanceLog")
@Access(AccessType.FIELD)
public class MaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaintenanceID", nullable = false, unique = true)
    private int MaintenanceID;

    @Column(name = "MaintenanceDate", nullable = false)
    private LocalDate MaintenanceDate;

    @ManyToOne
    @JoinColumn(name = "BRegistrationNo", referencedColumnName = "BRegistrationNo")
    private Bus bus;

    @Column(name = "MType", nullable = false, length = 30)
    private String MType;

    @Column(name = "MDescription", nullable = false, length = 255)
    private String Description;

    @Column(name = "Cost", nullable = false)
    private BigDecimal Cost;

    @Column(name = "Odometer", nullable = false)
    private BigDecimal Odometer;

    @Column(name = "NextServiceDue", nullable = false, length = 100)
    private String NextServiceDue;

    public MaintenanceLog() {
    }

    public int getMaintenanceID() {
        return MaintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        MaintenanceID = maintenanceID;
    }

    public LocalDate getMaintenanceDate() {
        return MaintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        MaintenanceDate = maintenanceDate;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getCost() {
        return Cost;
    }

    public void setCost(BigDecimal cost) {
        Cost = cost;
    }

    public BigDecimal getOdometer() {
        return Odometer;
    }

    public void setOdometer(BigDecimal odometer) {
        Odometer = odometer;
    }

    public String getNextServiceDue() {
        return NextServiceDue;
    }

    public void setNextServiceDue(String nextServiceDue) {
        NextServiceDue = nextServiceDue;
    }
}
