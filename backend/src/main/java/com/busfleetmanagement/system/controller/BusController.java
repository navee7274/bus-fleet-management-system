package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.BusRequest;
import com.busfleetmanagement.system.dto.BusResponse;
import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.BusService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    // CONVERT BUS TO BUS RESPONSE
    private BusResponse toResponse(Bus bus) {

        BusResponse response = new BusResponse();

        response.setBusRegistrationNo(bus.getBusRegistrationNo());
        response.setPurchaseDate(bus.getPurchaseDate());
        response.setPurchasePrice(bus.getPurchasePrice());
        response.setCapacity(bus.getCapacity());
        response.setNotes(bus.getNotes());
        response.setActive(bus.isActive());

        return response;
    }

    // CONVERT BUS REQUEST TO BUS ENTITY
    private Bus toEntity(BusRequest request) {

        System.out.println("bus toEntity....");
        Bus bus = new Bus();

        bus.setBusRegistrationNo(request.getBusRegistrationNo());
        bus.setPurchaseDate(request.getPurchaseDate());
        bus.setPurchasePrice(request.getPurchasePrice());
        bus.setCapacity(request.getCapacity());
        bus.setNotes(request.getNotes());
        bus.setActive(request.isActive());

        return bus;
    }

    // ADDING A BUS
    @PostMapping
    public ResponseEntity<BusResponse> createBus(
            @RequestBody BusRequest request) {

        System.out.println("REQUEST RECEIVED");
        System.out.println(request.getBusRegistrationNo());


        Bus bus = toEntity(request);

        Bus savedBus = busService.creatBus(bus);

        return ResponseEntity.ok(toResponse(savedBus));
    }

    // GET ALL BUSES
    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBus() {

        List<BusResponse> buses = busService.getAllBuses()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(buses);
    }

    // GET BUS BY REGISTRATION NUMBER
    @GetMapping("/{BRegistrationNo}")
    public ResponseEntity<BusResponse> getBusById(
            @PathVariable String BRegistrationNo) {

        Bus bus = busService.getBusById(BRegistrationNo)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Bus not found")
                );

        return ResponseEntity.ok(toResponse(bus));
    }

    // UPDATE BUS DETAILS
    @PutMapping("/{busRegistrationNo}")
    public ResponseEntity<BusResponse> updateBus(
            @PathVariable String busRegistrationNo,
            @RequestBody BusRequest request) {

        Bus bus = toEntity(request);

        System.out.println("bus update requested....");

        Bus updatedBus = busService.updateBus(busRegistrationNo, bus);

        return ResponseEntity.ok(toResponse(updatedBus));
    }

    // DEACTIVATE BUS
    @PutMapping("/{BRegistrationNo}/deactivate")
    public ResponseEntity<BusResponse> deactivateBus(
            @PathVariable String BRegistrationNo) {

        System.out.println("bus deactivation requested for " + BRegistrationNo + " ....");

        Bus bus = busService.deactivateBus(BRegistrationNo);

        return ResponseEntity.ok(toResponse(bus));
    }
}