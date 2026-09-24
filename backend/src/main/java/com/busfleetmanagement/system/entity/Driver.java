package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Driver")
@Access(AccessType.FIELD)

public class Driver {
    @Id
    @Column(name = "DriverID", nullable = false, unique = true, length = 10)
    private String DriverID;

    @Column(name = "Name", nullable = false, length = 100)
    private String Name;

    @Column(name = "NIC", nullable = false, length = 20)
    private String NIC;

    @Column(name = "ContactNo", nullable = false, length = 20)
    private String ContactNo;

    @Column(name = "BaseMonthlySalary", nullable = false)
    private BigDecimal BaseMonthlySalary;

    @Column(name = "PerTripAllowence", nullable = false)
    private BigDecimal PerTripAllowence;

    @Column(name = "Notes", nullable = false, length = 500)
    private String Notes;

    @Column(name = "DActive", nullable = false)
    private boolean DActive;

    public Driver() {

    }
    public String getDriverID() {return DriverID;}

    public void setDriverID(String DriverID) {this.DriverID = DriverID;}

    public String getName() {return Name;}

    public void setName(String Name) {this.Name = Name;}

    public String getNIC() {return NIC;}

    public void setNIC(String NIC) {this.NIC = NIC;}

    public String getContactNo() {return ContactNo;}

    public void setContactNo(String ContactNo) {this.ContactNo = ContactNo;}

    public BigDecimal getBaseMonthlySalary() {return BaseMonthlySalary;}

    public void setBaseMonthlySalary(BigDecimal BaseMonthlySalary) {this.BaseMonthlySalary = BaseMonthlySalary;}

    public BigDecimal getPerTripAllowence() {return PerTripAllowence;}

    public void setPerTripAllowence(BigDecimal PerTripAllowence) {this.PerTripAllowence = PerTripAllowence;}

    public String getNotes() {return Notes;}

    public void setNotes(String Notes) {this.Notes = Notes;}

    public boolean isDActive() {return DActive;}

    public void setDActive(boolean Active) {this.DActive = Active;}


}
