package com.busfleetmanagement.system.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SetPriceRequest {

    @NotNull
    private BigDecimal finalPrice;

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}