package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.ExpenceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenceLogRepository extends JpaRepository<ExpenceLog, Integer> {

}

