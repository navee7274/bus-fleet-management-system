package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MaintenanceLogRepository
        extends JpaRepository<MaintenanceLog, Integer> {

    List<MaintenanceLog> findByBus_BRegistrationNo(
            String registrationNo);

    List<MaintenanceLog> findByMType(String type);

    List<MaintenanceLog> findByMaintenanceDate(LocalDate date);

    List<MaintenanceLog> findByMaintenanceDateBetween(
            LocalDate startDate,
            LocalDate endDate);

    @Query("""
        SELECT COALESCE(SUM(m.Cost), 0)
        FROM MaintenanceLog m
        WHERE m.MaintenanceDate BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalMaintenanceCost(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}