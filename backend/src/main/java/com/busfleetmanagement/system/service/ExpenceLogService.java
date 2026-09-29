package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.Bus;
import com.busfleetmanagement.system.entity.ExpenceLog;
import com.busfleetmanagement.system.repository.BusRepository;
import com.busfleetmanagement.system.repository.ExpenceLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenceLogService {

    private final ExpenceLogRepository ExpenceLogRepository;
    private final BusRepository busRepository;

    public ExpenceLogService(
            ExpenceLogRepository ExpenceLogRepository,
            BusRepository busRepository) {

        this.ExpenceLogRepository = ExpenceLogRepository;
        this.busRepository = busRepository;
    }

    // Get all Expences
    public List<ExpenceLog> getAllExpences() {
        return ExpenceLogRepository.findAll();
    }

    // Get Expence by ID
    public ExpenceLog getExpenceById(int ExpenceID) {
        return ExpenceLogRepository.findById(ExpenceID)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expence not found with ID: " + ExpenceID));
    }

    // Create a new Expence
    public ExpenceLog createExpence(ExpenceLog ExpenceLog) {

        // Check bus
        if (ExpenceLog.getBus() == null ||
                ExpenceLog.getBus().getBusRegistrationNo() == null ||
                ExpenceLog.getBus().getBusRegistrationNo().isEmpty()) {

            throw new RuntimeException("Bus is required");
        }

        // Find bus
        Bus bus = busRepository
                .findById(ExpenceLog.getBus().getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        // Validate amount
        if (ExpenceLog.getAmount() == null ||
                ExpenceLog.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Expence amount must be greater than zero");
        }

        // Set managed bus entity
        ExpenceLog.setBus(bus);

        return ExpenceLogRepository.save(ExpenceLog);
    }

    // Update Expence
    public ExpenceLog updateExpence(
            int ExpenceID,
            ExpenceLog ExpenceLog) {

        // Find existing Expence
        ExpenceLog existingExpence =
                getExpenceById(ExpenceID);

        // Check bus
        if (ExpenceLog.getBus() == null ||
                ExpenceLog.getBus().getBusRegistrationNo() == null ||
                ExpenceLog.getBus().getBusRegistrationNo().isEmpty()) {

            throw new RuntimeException("Bus is required");
        }

        // Find bus
        Bus bus = busRepository
                .findById(ExpenceLog.getBus().getBusRegistrationNo())
                .orElseThrow(() ->
                        new RuntimeException("Bus not found"));

        // Validate amount
        if (ExpenceLog.getAmount() == null ||
                ExpenceLog.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Expence amount must be greater than zero");
        }

        // Update fields
        existingExpence.setExpencedate(
                ExpenceLog.getExpencedate());

        existingExpence.setBus(bus);

        existingExpence.setCategory(
                ExpenceLog.getCategory());

        existingExpence.setPaymentMethod(
                ExpenceLog.getPaymentMethod());

        existingExpence.setAmount(
                ExpenceLog.getAmount());

        existingExpence.setExpenceDescription(
                ExpenceLog.getExpenceDescription());

        return ExpenceLogRepository.save(existingExpence);
    }

    // Delete Expence
    public void deleteExpence(int ExpenceID) {

        ExpenceLog ExpenceLog =
                getExpenceById(ExpenceID);

        ExpenceLogRepository.delete(ExpenceLog);
    }
}

