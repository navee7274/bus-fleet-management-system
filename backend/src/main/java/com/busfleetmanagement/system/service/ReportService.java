package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.entity.*;
import com.busfleetmanagement.system.repository.*;
import com.busfleetmanagement.system.dto.report.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final JourneyRepository journeyRepository;
    private final FuelLogRepository fuelLogRepository;
    private final ExpenceLogRepository expenceLogRepository;
    private final MaintenanceLogRepository maintenanceLogRepository;

    public ReportService(
            BusRepository busRepository,
            DriverRepository driverRepository,
            JourneyRepository journeyRepository,
            FuelLogRepository fuelLogRepository,
            ExpenceLogRepository expenceLogRepository,
            MaintenanceLogRepository maintenanceLogRepository) {

        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.journeyRepository = journeyRepository;
        this.fuelLogRepository = fuelLogRepository;
        this.expenceLogRepository = expenceLogRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    // --------------------------------------------------
    // COMMON HELPERS
    // --------------------------------------------------

    private BigDecimal zero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private boolean inRange(
            LocalDate date,
            LocalDate startDate,
            LocalDate endDate) {

        if (date == null) {
            return false;
        }

        if (startDate != null && date.isBefore(startDate)) {
            return false;
        }

        if (endDate != null && date.isAfter(endDate)) {
            return false;
        }

        return true;
    }

    private BigDecimal getDistance(Journey journey) {
        if (journey.getKmTraveled() != null) {
            return zero(journey.getKmTraveled());
        }

        return zero(journey.getEndOdometer())
                .subtract(zero(journey.getStartOdometer()));
    }

    private BigDecimal sumFuelCost(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        BigDecimal totalCost = BigDecimal.ZERO;

        List<FuelLog> fuelLogs = fuelLogRepository.findAll();

        for (FuelLog fuelLog : fuelLogs) {

            // Skip if the bus is missing
            if (fuelLog.getBus() == null) {
                continue;
            }

            // Check whether this log belongs to the requested bus
            if (!registrationNo.equals(
                    fuelLog.getBus().getBusRegistrationNo())) {
                continue;
            }

            // Check whether the fuel date is within the requested range
            if (!inRange(fuelLog.getFDate(), startDate, endDate)) {
                continue;
            }

            // Get the fuel cost
            BigDecimal fuelCost = fuelLog.getFCost();

            // Treat null fuel costs as zero
            if (fuelCost == null) {
                fuelCost = BigDecimal.ZERO;
            }

            // Add the cost to the total
            totalCost = totalCost.add(fuelCost);
        }

        return totalCost;
    }

    private BigDecimal sumFuelLitres(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        return fuelLogRepository.findAll().stream()
                .filter(f -> f.getBus() != null)
                .filter(f -> registrationNo.equals(
                        f.getBus().getBusRegistrationNo()))
                .filter(f -> inRange(
                        f.getFDate(), startDate, endDate))
                .map(FuelLog::getFLitersfilled)
                .map(this::zero)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumExpenses(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        return expenceLogRepository.findAll().stream()
                .filter(e -> e.getBus() != null)
                .filter(e -> registrationNo.equals(
                        e.getBus().getBusRegistrationNo()))
                .filter(e -> inRange(
                        e.getExpencedate(), startDate, endDate))
                .map(ExpenceLog::getAmount)
                .map(this::zero)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumMaintenance(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        return maintenanceLogRepository.findAll().stream()
                .filter(m -> m.getBus() != null)
                .filter(m -> registrationNo.equals(
                        m.getBus().getBusRegistrationNo()))
                .filter(m -> inRange(
                        m.getMaintenanceDate(), startDate, endDate))
                .map(MaintenanceLog::getCost)
                .map(this::zero)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumIncome(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        return journeyRepository.findAll().stream()
                .filter(j -> j.getBus() != null)
                .filter(j -> registrationNo.equals(
                        j.getBus().getBusRegistrationNo()))
                .filter(j -> inRange(
                        j.getJourneyDate(), startDate, endDate))
                .map(Journey::getIncomeAmount)
                .map(this::zero)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumDistance(
            String registrationNo,
            LocalDate startDate,
            LocalDate endDate) {

        return journeyRepository.findAll().stream()
                .filter(j -> j.getBus() != null)
                .filter(j -> registrationNo.equals(
                        j.getBus().getBusRegistrationNo()))
                .filter(j -> inRange(
                        j.getJourneyDate(), startDate, endDate))
                .map(this::getDistance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // --------------------------------------------------
    // 1. BUS SUMMARY
    // --------------------------------------------------

    public List<BusSummaryResponse> getBusSummary(
            LocalDate startDate,
            LocalDate endDate) {

        List<BusSummaryResponse> result = new ArrayList<>();

        for (Bus bus : busRepository.findAll()) {

            String reg = bus.getBusRegistrationNo();

            BigDecimal income =
                    sumIncome(reg, startDate, endDate);

            BigDecimal fuelCost =
                    sumFuelCost(reg, startDate, endDate);

            BigDecimal maintenanceCost =
                    sumMaintenance(reg, startDate, endDate);

            BigDecimal otherExpenses =
                    sumExpenses(reg, startDate, endDate);

            BigDecimal totalCost = fuelCost
                    .add(maintenanceCost)
                    .add(otherExpenses);

            BigDecimal netProfit = income.subtract(totalCost);

            BigDecimal totalKm =
                    sumDistance(reg, startDate, endDate);

            BigDecimal fuelLitres =
                    sumFuelLitres(reg, startDate, endDate);

            BigDecimal fuelEconomy =
                    fuelLitres.compareTo(BigDecimal.ZERO) > 0
                            ? totalKm.divide(
                            fuelLitres,
                            2,
                            RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

            result.add(new BusSummaryResponse(
                    reg,
                    income,
                    fuelCost,
                    maintenanceCost,
                    otherExpenses,
                    totalCost,
                    netProfit,
                    totalKm,
                    fuelLitres,
                    fuelEconomy
            ));
        }

        return result;
    }

    // --------------------------------------------------
    // 2. MONTHLY SUMMARY
    // --------------------------------------------------

    public List<MonthlySummaryResponse> getMonthlySummary(
            int year,
            int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<MonthlySummaryResponse> result = new ArrayList<>();

        for (Bus bus : busRepository.findAll()) {

            String reg = bus.getBusRegistrationNo();

            BigDecimal income =
                    sumIncome(reg, startDate, endDate);

            BigDecimal fuelCost =
                    sumFuelCost(reg, startDate, endDate);

            BigDecimal maintenanceCost =
                    sumMaintenance(reg, startDate, endDate);

            BigDecimal otherExpenses =
                    sumExpenses(reg, startDate, endDate);

            BigDecimal totalCost = fuelCost
                    .add(maintenanceCost)
                    .add(otherExpenses);

            BigDecimal netProfit = income.subtract(totalCost);

            result.add(new MonthlySummaryResponse(
                    year,
                    month,
                    reg,
                    income,
                    totalCost,
                    netProfit
            ));
        }

        return result;
    }

    // --------------------------------------------------
    // 3. DRIVER SALARY
    // --------------------------------------------------

    public List<DriverSalaryResponse> getDriverSalary(
            int year,
            int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Journey> journeys = journeyRepository.findAll();

        List<DriverSalaryResponse> result = new ArrayList<>();

        for (Driver driver : driverRepository.findAll()) {

            long tripCount = journeys.stream()
                    .filter(j -> j.getDriver() != null)
                    .filter(j -> driver.getDriverID().equals(
                            j.getDriver().getDriverID()))
                    .filter(j -> inRange(
                            j.getJourneyDate(), startDate, endDate))
                    .count();

            BigDecimal baseSalary =
                    zero(driver.getBaseMonthlySalary());

            BigDecimal tripAllowance =
                    zero(driver.getPerTripAllowence())
                            .multiply(BigDecimal.valueOf(tripCount));

            BigDecimal monthlySalary =
                    baseSalary.add(tripAllowance);

            result.add(new DriverSalaryResponse(
                    driver.getDriverID(),
                    driver.getName(),
                    month,
                    baseSalary,
                    tripCount,
                    tripAllowance,
                    monthlySalary
            ));
        }

        return result;
    }
}
