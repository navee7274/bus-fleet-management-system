package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.JourneyRequest;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.Driver;
import com.busfleetmanagement.system.entity.Journey;
import com.busfleetmanagement.system.service.BusService;
import com.busfleetmanagement.system.service.DriverService;
import com.busfleetmanagement.system.service.JourneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/journeys")
@CrossOrigin
public class JourneyController {

    private final BusService busService;
    private final DriverService driverService;

    private final JourneyService journeyService;

    public JourneyController(BusService busService, DriverService driverService, JourneyService journeyService) {
        this.busService = busService;
        this.driverService = driverService;
        this.journeyService = journeyService;
    }

    private Journey convertToEntity(JourneyRequest request) {

        Bus bus = busService
                .getBusById(request.getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        Driver driver = driverService
                .getDriverById(request.getDriverID())
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        Journey journey = new Journey();

        journey.setJourneyID(request.getJourneyID());
        journey.setJourneyDate(request.getJourneyDate());
        journey.setBus(bus);
        journey.setDriver(driver);
        journey.setPurpose(request.getPurpose());
        journey.setClientDestination(request.getClientDestination());
        journey.setStartOdometer(request.getStartOdometer());
        journey.setEndOdometer(request.getEndOdometer());
        journey.setIncomeAmount(request.getIncomeAmount());
        journey.setNotes(request.getNotes());

        // Calculate KM travelled
        if (request.getStartOdometer() != null &&
                request.getEndOdometer() != null) {

            BigDecimal kmTravelled =
                    request.getEndOdometer()
                            .subtract(request.getStartOdometer());

            journey.setKmTraveled(kmTravelled);
        }

        return journey;
    }

    // Get all journeys
    @GetMapping
    public ResponseEntity<List<Journey>> getAllJourneys() {
        return ResponseEntity.ok(
                journeyService.getAllJourneys()
        );
    }

    // Get journey by ID
    @GetMapping("/{journeyID}")
    public ResponseEntity<Journey> getJourneyById(
            @PathVariable int journeyID) {

        return ResponseEntity.ok(
                journeyService.getJourneyById(journeyID)
        );
    }

    // Create journey
    @PostMapping
    public ResponseEntity<Journey> createJourney(
            @RequestBody JourneyRequest request) {

        Journey journey =
                journeyService.createJourney(convertToEntity(request));

        return new ResponseEntity<>(
                journey,
                HttpStatus.CREATED
        );
    }

    // Update journey
    @PutMapping("/{journeyID}")
    public ResponseEntity<Journey> updateJourney(
            @PathVariable int journeyID,
            @RequestBody JourneyRequest request) {

        Journey journey =
                journeyService.updateJourney(
                        journeyID,
                        convertToEntity(request)
                );

        return ResponseEntity.ok(journey);
    }

    // Delete journey
    @DeleteMapping("/{journeyID}")
    public ResponseEntity<Void> deleteJourney(
            @PathVariable int journeyID) {

        journeyService.deleteJourney(journeyID);

        return ResponseEntity.noContent().build();
    }
}
