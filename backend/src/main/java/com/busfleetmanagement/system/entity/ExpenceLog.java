package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ExpenceLog")
@Access(AccessType.FIELD)

public class ExpenceLog {

    @Id
    @Column(name = "ExpenceID", nullable = false, unique = true, length = 10)
    private int expenceID;

    @Column(name = "ExpenceDate", nullable = false, unique = false, length = 10)
    private LocalDate expencedate;

    @ManyToOne
    @JoinColumn(name = "BRegistrationNo", referencedColumnName = "BRegistrationNo")
    private Bus bus;

    @Column(name = "Category", nullable = false, unique = false, length = 10)
    private String category;

    @Column(name = "PaymentMethod", nullable = false, unique = false, length = 15)
    private String paymentMethod;

    @Column(name = "Amount", nullable = false, unique = false, length = 10)
    private BigDecimal amount;

    @Column(name = "ExpenseDescription", nullable = true, unique = false, length = 15)
    private String expenceDescription;

    public ExpenceLog(){
    }

    public int getExpenceID() {
        return expenceID;
    }

    public void setExpenceID(int expenceID) {
        this.expenceID = expenceID;
    }

    public LocalDate getExpencedate() {return expencedate;}

    public void setExpencedate(LocalDate expencedate) {
        this.expencedate = expencedate;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getExpenceDescription() {
        return expenceDescription;
    }

    public void setExpenceDescription(String expenceDescription) {
        this.expenceDescription = expenceDescription;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}