package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.MaintenanceLog;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.MaintenanceLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class DriverService {
    private final DriverRepository DriverRepository;

    public DriverService(DriverRepository DriverRepository) {
        this.DriverRepository = DriverRepository;
    }

    private String generateDriverId() {

        System.out.println("Generating Driver ID...");

        List<Driver> drivers = DriverRepository.findAll();

        if (drivers.isEmpty()) {
            return "DRV001";
        }

        int highestNumber = 0;

        for (Driver driver : drivers) {

            String driverID = driver.getDriverID();

            if (driverID != null && driverID.matches("DRV\\d+")) {

                int number = Integer.parseInt(driverID.substring(3));

                if (number > highestNumber) {
                    highestNumber = number;
                }
            }
        }

        return String.format("DRV%03d", highestNumber + 1);
    }

    //CREAT DRIVER RECORD
    public Driver creatDriver(Driver driver) {

        System.out.println("========== CREATE DRIVER ==========");

        System.out.println("Received driver:");
        System.out.println("Name: " + driver.getName());
        System.out.println("NIC: " + driver.getNIC());
        System.out.println("Contact: " + driver.getContactNo());
        System.out.println("Salary: " + driver.getBaseMonthlySalary());
        System.out.println("Allowance: " + driver.getPerTripAllowence());
        System.out.println("Active: " + driver.isDActive());

        System.out.println("Generating Driver ID...");

        String driverID = generateDriverId();

        System.out.println("Generated Driver ID: " + driverID);

        driver.setDriverID(driverID);

        System.out.println("Saving driver...");

        Driver savedDriver = DriverRepository.save(driver);

        System.out.println("Driver saved successfully!");

        return savedDriver;
    }

    // READ ALL Driver RECORDS
    public List<Driver> getAllDriver() {
        return DriverRepository.findAll();
    }

    // READ ONLY ONE Driver RECORD
    public Optional<Driver> getDriverById(String DriverID) {
        return DriverRepository.findById(DriverID);
    }

    //UPDATE DRIVER RECORDS

    public Driver updateDriver(String DriverID, Driver driver) {
        Driver existingRecord = DriverRepository.findById(DriverID)
                .orElseThrow(() -> new RuntimeException("Driver record not found"));

        existingRecord.setName(driver.getName());
        existingRecord.setNIC(driver.getNIC());
        existingRecord.setContactNo(driver.getContactNo());
        existingRecord.setBaseMonthlySalary(driver.getBaseMonthlySalary());
        existingRecord.setPerTripAllowence(driver.getPerTripAllowence());
        existingRecord.setNotes(driver.getNotes());
        existingRecord.setDActive(driver.isDActive());

        return DriverRepository.save(existingRecord);
    }

    // DEACTIVATE DRIVER
    public Driver deactivateDriver(String DriverID) {

        Driver existingRecord = DriverRepository.findById(DriverID)
                .orElseThrow(() ->
                        new RuntimeException("Driver record not found"));

        existingRecord.setDActive(false);

        return DriverRepository.save(existingRecord);
    }
}
