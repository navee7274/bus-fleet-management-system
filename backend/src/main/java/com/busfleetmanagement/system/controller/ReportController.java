package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.service.ReportService;
import com.busfleetmanagement.system.dto.report.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 1. Bus Summary Report
    @GetMapping("/bus-summary")
    public List<BusSummaryResponse> getBusSummary(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return reportService.getBusSummary(startDate, endDate);
    }

    // 2. Monthly Summary Report
    @GetMapping("/monthly-summary")
    public List<MonthlySummaryResponse> getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {

        return reportService.getMonthlySummary(year, month);
    }

    // 3. Driver Salary Report
    @GetMapping("/driver-salary")
    public List<DriverSalaryResponse> getDriverSalary(
            @RequestParam int year,
            @RequestParam int month) {

        return reportService.getDriverSalary(year, month);
    }
}