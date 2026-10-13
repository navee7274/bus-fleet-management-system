package com.busfleetmanagement.system.dto.report;

import java.math.BigDecimal;

public class DriverSalaryResponse {

    private String driverID;
    private String driverName;
    private int month;
    private BigDecimal baseSalary;
    private long tripCount;
    private BigDecimal tripAllowance;
    private BigDecimal monthlySalary;

    public DriverSalaryResponse() {
    }

    public DriverSalaryResponse(
            String driverID,
            String driverName,
            int month,
            BigDecimal baseSalary,
            long tripCount,
            BigDecimal tripAllowance,
            BigDecimal monthlySalary
    ) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.month = month;
        this.baseSalary = baseSalary;
        this.tripAllowance = tripAllowance;
        this.monthlySalary = monthlySalary;
        this.tripCount = tripCount;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getTripAllowance() {
        return tripAllowance;
    }

    public void setTripAllowance(BigDecimal tripAllowance) {
        this.tripAllowance = tripAllowance;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public long getTripCount() {
        return tripCount;
    }

    public void setTripCount(long tripCount) {
        this.tripCount = tripCount;
    }
}