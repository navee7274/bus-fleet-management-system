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
    public Optional<MaintenanceLog> geteMaintenancRecordById(int MaintenanceID) {
        return maintenanceRepository.findById(MaintenanceID);
    }

    // UPDATE MAINTENANCE RECORD
    public MaintenanceLog updateMaintenanceRecord(int MaintenanceID, MaintenanceLog maintenanceRecord) {

        MaintenanceLog existingRecord = maintenanceRepository.findById(MaintenanceID)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        existingRecord.setMaintenanceDate(maintenanceRecord.getMaintenanceDate());
        existingRecord.setBus(maintenanceRecord.getBus());
        existingRecord.setMType(maintenanceRecord.getMType());
        existingRecord.setDescription(maintenanceRecord.getDescription());
        existingRecord.setCost(maintenanceRecord.getCost());
        existingRecord.setOdometer(maintenanceRecord.getOdometer());
        existingRecord.setNextServiceDue(maintenanceRecord.getNextServiceDue());

        return maintenanceRepository.save(existingRecord);
    }
}
