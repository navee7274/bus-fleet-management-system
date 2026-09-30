package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.BusAvailabilityRequest;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin(origins = "*")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(
            AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/buses")
    public ResponseEntity<List<Bus>> getAvailableBuses(
            @RequestBody BusAvailabilityRequest request) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Availability request cannot be empty."
            );
        }

        if (request.getStartDateTime() == null) {
            throw new IllegalArgumentException(
                    "Start date and time are required."
            );
        }

        if (request.getEndDateTime() == null) {
            throw new IllegalArgumentException(
                    "End date and time are required."
            );
        }

        if (!request.getStartDateTime()
                .isBefore(request.getEndDateTime())) {

            throw new IllegalArgumentException(
                    "End date and time must be after start date and time."
            );
        }

        if (request.getPassengerCount() <= 0) {
            throw new IllegalArgumentException(
                    "Passenger count must be greater than zero."
            );
        }

        List<Bus> availableBuses =
                availabilityService.getAvailableBuses(
                        request.getStartDateTime(),
                        request.getEndDateTime(),
                        request.getPassengerCount()
                );

        return ResponseEntity.ok(availableBuses);
    }

    // Handle invalid request data
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}