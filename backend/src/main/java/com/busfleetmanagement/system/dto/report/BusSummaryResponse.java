package com.busfleetmanagement.system.dto.report;

import java.math.BigDecimal;

public class BusSummaryResponse {

    private String registrationNo;
    private BigDecimal income;
    private BigDecimal fuelCost;
    private BigDecimal maintenanceCost;
    private BigDecimal otherExpenses;
    private BigDecimal totalCost;
    private BigDecimal netProfit;
    private BigDecimal totalKm;
    private BigDecimal fuelLitres;
    private BigDecimal fuelEconomyKmPerLitre;

    public BusSummaryResponse() {
    }

    public BusSummaryResponse(
            String registrationNo,
            BigDecimal income,
            BigDecimal fuelCost,
            BigDecimal maintenanceCost,
            BigDecimal otherExpenses,
            BigDecimal totalCost,
            BigDecimal netProfit,
            BigDecimal totalKm,
            BigDecimal fuelLitres,
            BigDecimal fuelEconomyKmPerLitre
    ) {
        this.registrationNo = registrationNo;
        this.income = income;
        this.fuelCost = fuelCost;
        this.maintenanceCost = maintenanceCost;
        this.otherExpenses = otherExpenses;
        this.totalCost = totalCost;
        this.netProfit = netProfit;
        this.totalKm = totalKm;
        this.fuelLitres = fuelLitres;
        this.fuelEconomyKmPerLitre = fuelEconomyKmPerLitre;
    }

    // Getters and Setters

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

    public BigDecimal getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(BigDecimal fuelCost) {
        this.fuelCost = fuelCost;
    }

    public BigDecimal getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(BigDecimal maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public BigDecimal getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(BigDecimal otherExpenses) {
        this.otherExpenses = otherExpenses;
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

    public BigDecimal getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(BigDecimal totalKm) {
        this.totalKm = totalKm;
    }

    public BigDecimal getFuelLitres() {
        return fuelLitres;
    }

    public void setFuelLitres(BigDecimal fuelLitres) {
        this.fuelLitres = fuelLitres;
    }

    public BigDecimal getFuelEconomyKmPerLitre() {
        return fuelEconomyKmPerLitre;
    }

    public void setFuelEconomyKmPerLitre(BigDecimal fuelEconomyKmPerLitre) {
        this.fuelEconomyKmPerLitre = fuelEconomyKmPerLitre;
    }
}