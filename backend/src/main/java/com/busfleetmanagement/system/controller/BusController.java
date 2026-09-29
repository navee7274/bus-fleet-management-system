package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.exception.ResourceNotFoundException;
import com.busfleetmanagement.system.service.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.busfleetmanagement.system.dto.BusResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bus")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

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

    private Optional<BusResponse> toResponse(Optional<Bus> bus) {
        return bus.map(this::toResponse);
    }

    private List<BusResponse> toResponse(List<Bus> buses) {
        return buses.stream()
                .map(this::toResponse)
                .toList();
    }

    //ADDING A BUS..................................
    @PostMapping
    public ResponseEntity<Bus> creatBus(@RequestBody Bus bus) {
        Bus savedBus = busService.creatBus(bus);
        return ResponseEntity.ok(savedBus);
    }

    //GET ALL BUSSES................................
    @GetMapping
    public ResponseEntity<List<Bus>> getAllBus() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    //GET BUS BY REGISTRATION NUMBER................
    @GetMapping("/{BRegistrationNo}")
    public ResponseEntity<Bus> getBusById(
            @PathVariable String BRegistrationNo) {

        Bus bus = busService.getBusById(BRegistrationNo)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Bus not found")
                );

        return ResponseEntity.ok(bus);
    }

    //UPDATE BUS DETAILS...........................
    @PutMapping("/{BRegistrationNo}")
    public ResponseEntity<Bus> updateBus(
            @PathVariable String BRegistrationNo,
            @RequestBody Bus bus) {
        return ResponseEntity.ok(
                busService.updateBus(BRegistrationNo, bus)
        );
    }
    //DEACTIVATE BUS..............................
    @PatchMapping("/{BRegistrationNo}/deactivate")
    public ResponseEntity<Bus> deactivateBus(
            @PathVariable String BRegistrationNo){
            return ResponseEntity.ok(
                    busService.deactivateBus(BRegistrationNo)
            );

    }
}

