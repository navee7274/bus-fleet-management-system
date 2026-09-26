package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.MaintenanceLog;
import com.busfleetmanagement.system.repository.DriverRepository;
import com.busfleetmanagement.system.repository.MaintenanceLogRepository;

import java.util.List;
import java.util.Optional;

public class DriverService {
    private final DriverRepository DriverRepository;

    public DriverService(DriverRepository DriverRepository) {
        this.DriverRepository = DriverRepository;
    }

    //CREAT DRIVER RECORD
    public Driver creatDriverRecord(Driver driver) {
        return DriverRepository.save(driver);
    }

    // READ ALL Driver RECORDS
    public List<Driver> getAllDriverRecords() {
        return DriverRepository.findAll();
    }

    // READ ONLY ONE Driver RECORD
    public Optional<Driver> getDriverRecordById(String DriverID) {
        return DriverRepository.findById(DriverID);
    }

    //UPDATE DRIVER RECORDS

    public Driver updateDriver(String DriverID, Driver driver) {
        Driver existingRecord = DriverRepository.findById(DriverID)
                .orElseThrow(() -> new RuntimeException("Driver record not found"));

        existingRecord.setDriverID(driver.getDriverID());
        existingRecord.setName(driver.getName());
        existingRecord.setNIC(driver.getNIC());
        existingRecord.setContactNo(driver.getContactNo());
        existingRecord.setBaseMonthlySalary(driver.getBaseMonthlySalary());
        existingRecord.setPerTripAllowence(driver.getPerTripAllowence());
        existingRecord.setNotes(driver.getNotes());
        existingRecord.setDActive(driver.isDActive());

        return DriverRepository.save(existingRecord);
    }
}
