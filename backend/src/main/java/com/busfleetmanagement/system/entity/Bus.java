package com.busfleetmanagement.system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Bus")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BusID")
    private Integer busId;

    @Column(name = "BusNo", nullable = false, unique = true, length = 10)
    private String busNo;

    @Column(name = "RegistrationNo", nullable = false, unique = true, length = 20)
    private String registrationNo;

    @Column(name = "Model", length = 50)
    private String model;

    @Column(name = "Capacity")
    private Integer capacity;

    @Column(name = "Status", nullable = false, length = 20)
    private String status;

    public Bus() {
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}