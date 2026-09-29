package com.busfleetmanagement.system.dto;

import java.math.BigDecimal;

public class DriverResponse {

    private String DriverID;
    private String Name;
    private String NIC;
    private String ContactNo;
    private BigDecimal BaseMonthlySalary;
    private BigDecimal PerTripAllowence;
    private String Notes;
    private boolean DActive;

    public DriverResponse() {
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String DriverID) {
        this.DriverID = DriverID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String ContactNo) {
        this.ContactNo = ContactNo;
    }

    public BigDecimal getBaseMonthlySalary() {
        return BaseMonthlySalary;
    }

    public void setBaseMonthlySalary(BigDecimal BaseMonthlySalary) {
        this.BaseMonthlySalary = BaseMonthlySalary;
    }

    public BigDecimal getPerTripAllowence() {
        return PerTripAllowence;
    }

    public void setPerTripAllowence(BigDecimal PerTripAllowence) {
        this.PerTripAllowence = PerTripAllowence;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    public boolean isDActive() {
        return DActive;
    }

    public void setDActive(boolean DActive) {
        this.DActive = DActive;
    }
}