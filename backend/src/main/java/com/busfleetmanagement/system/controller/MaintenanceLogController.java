package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.MaintenanceLogRequest;
import com.busfleetmanagement.system.entity.MaintenanceLog;
import com.busfleetmanagement.system.service.MaintenanceLogService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-log")
@CrossOrigin
public class MaintenanceLogController {

    private final MaintenanceLogService maintenanceLogService;

    public MaintenanceLogController(
            MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<MaintenanceLog> createMaintenanceLog(
            @RequestBody MaintenanceLogRequest request) {

        MaintenanceLog created =
                maintenanceLogService.createMaintenanceLog(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<MaintenanceLog>> getAllMaintenanceLogs() {
        return ResponseEntity.ok(
                maintenanceLogService.getAllMaintenanceLogs());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceLog> getMaintenanceLogById(
            @PathVariable int id) {

        return ResponseEntity.ok(
                maintenanceLogService.getMaintenanceLogById(id));
    }

    // GET BY BUS
    @GetMapping("/bus/{registrationNo}")
    public ResponseEntity<List<MaintenanceLog>> getMaintenanceLogsByBus(
            @PathVariable String registrationNo) {

        return ResponseEntity.ok(
                maintenanceLogService.getMaintenanceLogsByBus(registrationNo));
    }

    // GET BY TYPE
    @GetMapping("/type/{type}")
    public ResponseEntity<List<MaintenanceLog>> getMaintenanceLogsByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                maintenanceLogService.getMaintenanceLogsByType(type));
    }

    // GET BY DATE
    @GetMapping("/date/{date}")
    public ResponseEntity<List<MaintenanceLog>> getMaintenanceLogsByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                maintenanceLogService.getMaintenanceLogsByDate(date));
    }

    // GET BY DATE RANGE
    @GetMapping("/date-range")
    public ResponseEntity<List<MaintenanceLog>> getMaintenanceLogsBetweenDates(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                maintenanceLogService.getMaintenanceLogsBetweenDates(
                        startDate, endDate));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceLog> updateMaintenanceLog(
            @PathVariable int id,
            @RequestBody MaintenanceLogRequest request) {

        return ResponseEntity.ok(
                maintenanceLogService.updateMaintenanceLog(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceLog(
            @PathVariable int id) {

        maintenanceLogService.deleteMaintenanceLog(id);
        return ResponseEntity.noContent().build();
    }

    // TOTAL MAINTENANCE COST
    @GetMapping("/total-cost")
    public ResponseEntity<BigDecimal> getTotalMaintenanceCost(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                maintenanceLogService.getTotalMaintenanceCost(
                        startDate, endDate));
    }
}