package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.MaintenanceLog;
import com.busfleetmanagement.system.repository.MaintenanceLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    private final MaintenanceLogRepository maintenanceRepository;

    public MaintenanceService(MaintenanceLogRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    // CREATE MAINTENANCE RECORD
    public MaintenanceLog createMaintenanceRecord(MaintenanceLog maintenanceRecord) {
        return maintenanceRepository.save(maintenanceRecord);
    }

    // READ ALL MAINTENANCE RECORDS
    public List<MaintenanceLog> getAllMaintenanceRecords() {
        return maintenanceRepository.findAll();
    }

    // READ ONLY ONE MAINTENANCE RECORD
    public Optional<MaintenanceLog> getMaintenanceRecordById(int MaintenanceID) {
        return maintenanceRepository.findById(MaintenanceID);
    }

    // UPDATE MAINTENANCE RECORD
    public MaintenanceLog updateBus(int MaintenanceID, MaintenanceLog bus) {

        MaintenanceLog existingRecord = maintenanceRepository.findById(MaintenanceID)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        existingRecord.setMaintenanceDate(bus.getMaintenanceDate());
        existingRecord.setBus(bus.getBus());
        existingRecord.setMType(bus.getMType());
        existingRecord.setDescription(bus.getDescription());
        existingRecord.setCost(bus.getCost());
        existingRecord.setOdometer(bus.getOdometer());
        existingRecord.setNextServiceDue(bus.getNextServiceDue());

        return maintenanceRepository.save(existingRecord);
    }
}
