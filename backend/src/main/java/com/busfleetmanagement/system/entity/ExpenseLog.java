package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ExpenceLog")
@Access(AccessType.FIELD)

public class ExpenseLog {

    @Id
    @Column(name = "ExpenseID", nullable = false, unique = true, length = 10)
    private int expenseID;

    @Column(name = "ExpenseDate", nullable = false, unique = false, length = 10)
    private LocalDate expensedate;

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
    private String expenseDescription;

    public ExpenseLog(){
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public LocalDate getExpensedate() {return expensedate;}

    public void setExpensedate(LocalDate expensedate) {
        this.expensedate = expensedate;
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

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}