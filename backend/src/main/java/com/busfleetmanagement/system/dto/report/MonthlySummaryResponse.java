package com.busfleetmanagement.system.dto.report;

import java.math.BigDecimal;

public class MonthlySummaryResponse {

    private int year;
    private int month;
    private String registrationNo;
    private BigDecimal income;
    private BigDecimal totalCost;
    private BigDecimal netProfit;

    public MonthlySummaryResponse() {
    }

    public MonthlySummaryResponse(
            int year,
            int month,
            String registrationNo,
            BigDecimal income,
            BigDecimal totalCost,
            BigDecimal netProfit
    ) {
        this.year = year;
        this.month = month;
        this.registrationNo = registrationNo;
        this.income = income;
        this.totalCost = totalCost;
        this.netProfit = netProfit;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }
}