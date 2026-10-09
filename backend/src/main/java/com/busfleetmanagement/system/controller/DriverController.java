package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.DriverRequest;
import com.busfleetmanagement.system.dto.DriverResponse;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.DriverService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // CONVERT DRIVER TO DRIVER RESPONSE
    private DriverResponse toResponse(Driver driver) {

        DriverResponse response = new DriverResponse();

        response.setDriverID(driver.getDriverID());
        response.setName(driver.getName());
        response.setNIC(driver.getNIC());
        response.setContactNo(driver.getContactNo());
        response.setBaseMonthlySalary(driver.getBaseMonthlySalary());
        response.setPerTripAllowence(driver.getPerTripAllowence());
        response.setNotes(driver.getNotes());
        response.setDActive(driver.isDActive());

        return response;
    }

    // CONVERT DRIVER REQUEST TO DRIVER ENTITY
    private Driver toEntity(DriverRequest request) {

        Driver driver = new Driver();

        driver.setName(request.getName());
        driver.setNIC(request.getNIC());
        driver.setContactNo(request.getContactNo());
        driver.setBaseMonthlySalary(request.getBaseMonthlySalary());
        driver.setPerTripAllowence(request.getPerTripAllowence());
        driver.setNotes(request.getNotes());
        driver.setDActive(request.isDActive());

        return driver;
    }

    // ADDING A DRIVER
    @PostMapping
    public ResponseEntity<DriverResponse> creatDriver(
            @RequestBody DriverRequest request) {

        Driver driver = toEntity(request);

        Driver savedDriver = driverService.creatDriver(driver);

        return ResponseEntity.ok(toResponse(savedDriver));
    }

    // GET ALL DRIVERS
    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {

        List<DriverResponse> response = driverService
                .getAllDriver()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET DRIVER BY ID
    @GetMapping("/{DriverID}")
    public ResponseEntity<DriverResponse> getDriverById(
            @PathVariable String DriverID) {

        Driver driver = driverService
                .getDriverById(DriverID)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Driver not found"
                        )
                );

        return ResponseEntity.ok(toResponse(driver));
    }

    // UPDATE DRIVER DETAILS
    @PutMapping("/{DriverID}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable String DriverID,
            @RequestBody DriverRequest request) {

        Driver driver = toEntity(request);

        Driver updatedDriver = driverService
                .updateDriver(DriverID, driver);

        return ResponseEntity.ok(toResponse(updatedDriver));
    }

    // DEACTIVATE DRIVER
    @PatchMapping("/{DriverID}/deactivate")
    public ResponseEntity<DriverResponse> deactivateDriver(
            @PathVariable String DriverID) {

        Driver driver = driverService.deactivateDriver(DriverID);

        return ResponseEntity.ok(toResponse(driver));
    }
}