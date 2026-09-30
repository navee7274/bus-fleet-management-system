package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.entity.FuelLog;
import com.busfleetmanagement.system.service.FuelLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuel-logs")
@CrossOrigin
public class FuelLogController {

    private final FuelLogService fuelLogService;

    public FuelLogController(FuelLogService fuelLogService) {
        this.fuelLogService = fuelLogService;
    }

    // Get all fuel logs
    @GetMapping
    public ResponseEntity<List<FuelLog>> getAllFuelLogs() {

        return ResponseEntity.ok(
                fuelLogService.getAllFuelLogs()
        );
    }

    // Get fuel log by ID
    @GetMapping("/{fuelLogID}")
    public ResponseEntity<FuelLog> getFuelLogById(
            @PathVariable int fuelLogID) {

        return ResponseEntity.ok(
                fuelLogService.getFuelLogById(fuelLogID)
        );
    }

    // Create fuel log
    @PostMapping
    public ResponseEntity<FuelLog> createFuelLog(
            @RequestBody FuelLog fuelLog) {

        FuelLog createdFuelLog = fuelLogService.createFuelLog(fuelLog);

        return new ResponseEntity<>(
                createdFuelLog,
                HttpStatus.CREATED
        );
    }

    // Update fuel log
    @PutMapping("/{fuelLogID}")
    public ResponseEntity<FuelLog> updateFuelLog(
            @PathVariable int fuelLogID,
            @RequestBody FuelLog fuelLog) {

        FuelLog updatedFuelLog = fuelLogService.updateFuelLog(
                        fuelLogID,
                        fuelLog
                );

        return ResponseEntity.ok(updatedFuelLog);
    }

    // Delete fuel log
    @DeleteMapping("/{fuelLogID}")
    public ResponseEntity<Void> deleteFuelLog(
            @PathVariable int fuelLogID) {

        fuelLogService.deleteFuelLog(fuelLogID);

        return ResponseEntity.noContent().build();
    }
}
