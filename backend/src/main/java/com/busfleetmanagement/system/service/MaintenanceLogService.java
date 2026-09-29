package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.dto.MaintenanceLogRequest;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.MaintenanceLog;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.MaintenanceLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final BusRepository busRepository;

    public MaintenanceLogService(
            MaintenanceLogRepository maintenanceLogRepository,
            BusRepository busRepository) {

        this.maintenanceLogRepository = maintenanceLogRepository;
        this.busRepository = busRepository;
    }

    // =========================
    // CREATE
    // =========================

    public MaintenanceLog createMaintenanceLog(MaintenanceLogRequest request) {

        Bus bus = busRepository.findById(request.getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found: "
                                + request.getBusRegistrationNo()));

        MaintenanceLog log = new MaintenanceLog();

        log.setMaintenanceDate(request.getMaintenanceDate());
        log.setBus(bus);
        log.setMType(request.getMType());
        log.setDescription(request.getDescription());
        log.setCost(request.getCost());
        log.setOdometer(request.getOdometer());
        log.setNextServiceDue(request.getNextServiceDue());

        return maintenanceLogRepository.save(log);
    }

    // =========================
    // GET BY ID
    // =========================

    public MaintenanceLog getMaintenanceLogById(int id) {

        return maintenanceLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Maintenance log not found: " + id));
    }

    // =========================
    // GET ALL
    // =========================

    public List<MaintenanceLog> getAllMaintenanceLogs() {
        return maintenanceLogRepository.findAll();
    }

    // =========================
    // GET BY BUS
    // =========================

    public List<MaintenanceLog> getMaintenanceLogsByBus(String registrationNo) {

        if (!busRepository.existsById(registrationNo)) {
            throw new RuntimeException("Bus not found: " + registrationNo);
        }

        return maintenanceLogRepository
                .findByBus_BRegistrationNo(registrationNo);
    }

    // =========================
    // GET BY TYPE
    // =========================

    public List<MaintenanceLog> getMaintenanceLogsByType(String type) {
        return maintenanceLogRepository.findByMType(type);
    }

    // =========================
    // GET BY DATE
    // =========================

    public List<MaintenanceLog> getMaintenanceLogsByDate(LocalDate date) {
        return maintenanceLogRepository.findByMaintenanceDate(date);
    }

    // =========================
    // GET BY DATE RANGE
    // =========================

    public List<MaintenanceLog> getMaintenanceLogsBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        return maintenanceLogRepository
                .findByMaintenanceDateBetween(startDate, endDate);
    }

    // =========================
    // UPDATE
    // =========================

    public MaintenanceLog updateMaintenanceLog(
            int id,
            MaintenanceLogRequest request) {

        MaintenanceLog log = getMaintenanceLogById(id);

        Bus bus = busRepository.findById(request.getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found: "
                                + request.getBusRegistrationNo()));

        log.setMaintenanceDate(request.getMaintenanceDate());
        log.setBus(bus);
        log.setMType(request.getMType());
        log.setDescription(request.getDescription());
        log.setCost(request.getCost());
        log.setOdometer(request.getOdometer());
        log.setNextServiceDue(request.getNextServiceDue());

        return maintenanceLogRepository.save(log);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteMaintenanceLog(int id) {

        if (!maintenanceLogRepository.existsById(id)) {
            throw new RuntimeException(
                    "Maintenance log not found: " + id);
        }

        maintenanceLogRepository.deleteById(id);
    }

    // =========================
    // TOTAL MAINTENANCE COST
    // =========================

    public java.math.BigDecimal getTotalMaintenanceCost(
            LocalDate startDate,
            LocalDate endDate) {

        return maintenanceLogRepository
                .getTotalMaintenanceCost(startDate, endDate);
    }
}