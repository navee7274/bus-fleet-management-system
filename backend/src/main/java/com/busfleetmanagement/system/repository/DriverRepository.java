package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String> {
}
