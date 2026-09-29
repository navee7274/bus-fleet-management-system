package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.FuelLog;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.FuelLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FuelLogService {

    private final FuelLogRepository fuelLogRepository;
    private final BusRepository busRepository;

    public FuelLogService(
            FuelLogRepository fuelLogRepository,
            BusRepository busRepository) {

        this.fuelLogRepository = fuelLogRepository;
        this.busRepository = busRepository;
    }

    // Get all fuel logs
    public List<FuelLog> getAllFuelLogs() {
        return fuelLogRepository.findAll();
    }

    // Get fuel log by ID
    public FuelLog getFuelLogById(int fuelLogID) {
        return fuelLogRepository.findById(fuelLogID)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Fuel log not found with ID: " + fuelLogID));
    }

    // Create a new fuel log
    public FuelLog createFuelLog(FuelLog fuelLog) {

        // Check bus
        if (fuelLog.getBus() == null ||
                fuelLog.getBus().getBusRegistrationNo() == null ||
                fuelLog.getBus().getBusRegistrationNo().isEmpty()) {

            throw new RuntimeException("Bus is required");
        }

        // Find bus
        Bus bus = busRepository
                .findById(fuelLog.getBus().getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        // Validate fuel price
        if (fuelLog.getfPrice() == null ||
                fuelLog.getfPrice().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Fuel price must be greater than zero");
        }

        // Validate liters
        if (fuelLog.getfLitersfilled() == null ||
                fuelLog.getfLitersfilled().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Fuel liters must be greater than zero");
        }

        // Calculate fuel cost
        BigDecimal fuelCost =
                fuelLog.getfPrice()
                        .multiply(fuelLog.getfLitersfilled());

        fuelLog.setfCost(fuelCost);

        // Set managed bus entity
        fuelLog.setBus(bus);

        return fuelLogRepository.save(fuelLog);
    }

    // Update fuel log
    public FuelLog updateFuelLog(
            int fuelLogID,
            FuelLog fuelLog) {

        // Find existing fuel log
        FuelLog existingFuelLog =
                getFuelLogById(fuelLogID);

        // Check bus
        if (fuelLog.getBus() == null ||
                fuelLog.getBus().getBusRegistrationNo() == null ||
                fuelLog.getBus().getBusRegistrationNo().isEmpty()) {

            throw new RuntimeException("Bus is required");
        }

        // Find bus
        Bus bus = busRepository
                .findById(fuelLog.getBus().getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        // Validate fuel price
        if (fuelLog.getfPrice() == null ||
                fuelLog.getfPrice().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Fuel price must be greater than zero");
        }

        // Validate liters
        if (fuelLog.getfLitersfilled() == null ||
                fuelLog.getfLitersfilled().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Fuel liters must be greater than zero");
        }

        // Calculate fuel cost
        BigDecimal fuelCost =
                fuelLog.getfPrice()
                        .multiply(fuelLog.getfLitersfilled());

        // Update fields
        existingFuelLog.setBus(bus);
        existingFuelLog.setfDate(fuelLog.getfDate());
        existingFuelLog.setfPrice(fuelLog.getfPrice());
        existingFuelLog.setfLitersfilled(
                fuelLog.getfLitersfilled());
        existingFuelLog.setfCost(fuelCost);

        return fuelLogRepository.save(existingFuelLog);
    }

    // Delete fuel log
    public void deleteFuelLog(int fuelLogID) {

        FuelLog fuelLog =
                getFuelLogById(fuelLogID);

        fuelLogRepository.delete(fuelLog);
    }
}