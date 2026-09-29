package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.busfleetmanagement.system.dto.DriverResponse;

import java.util.List;

public class DriverController {
    private final DriverService driverService;
    public DriverController(DriverService driverService) {
        this.driverService = driverService;

    }

    //ADDING A DRIVER..................................
    @PostMapping
    public ResponseEntity<Driver> creatDriver
        (@RequestBody Driver driver) {
        return ResponseEntity.ok(
                driverService.creatDriver(driver)
        );
    }

    //GET ALL DRIVERS..................................
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers(){
        return ResponseEntity.ok(
                driverService.getAllDriver()
        );
    }

    // GET DRIVER BY ID................................
    @GetMapping("/{DriverID}")
    public ResponseEntity<Driver> getDriverById(
            @PathVariable String DriverID) {
        return ResponseEntity.ok(
                driverService.getDriverById(DriverID).orElseThrow(
                        () -> new ResourceNotFoundException("Driver not found")
                )
        );
    }

    // UPDATE DETAILS...................................
    @PutMapping("/{DriverID}")
    public ResponseEntity<Driver> updateDriver(
            @PathVariable String DriverID,
            @RequestBody Driver driver) {
        return ResponseEntity.ok(
                driverService.updateDriver(DriverID, driver));
    }

    // DEACTIVATE DRIVER.................................
    @PatchMapping("/{DriverID}/deactivate")
    public ResponseEntity<Driver> deactivateDriver(
            @PathVariable String DriverID){
        return ResponseEntity.ok(
                driverService.deactivateDriver(DriverID)
        );

    }


}
