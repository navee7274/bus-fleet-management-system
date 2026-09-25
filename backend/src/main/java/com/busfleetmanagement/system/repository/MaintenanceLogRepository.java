package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Integer> {
}
