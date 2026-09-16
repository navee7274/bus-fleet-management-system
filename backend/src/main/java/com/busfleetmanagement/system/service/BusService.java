package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;

import java.util.List;

public interface BusService {

    // Create a new bus
    Bus createBus(Bus bus);

    // Get all buses
    List<Bus> getAllBuses();

    // Get a bus by ID
    Bus getBusById(Integer busId);

    // Update an existing bus
    Bus updateBus(Integer busId, Bus bus);

    // Delete a bus
    void deleteBus(Integer busId);
}