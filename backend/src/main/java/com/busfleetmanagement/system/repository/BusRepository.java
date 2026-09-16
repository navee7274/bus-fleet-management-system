package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

    Optional<Bus> findByBusNo(String busNo);

    Optional<Bus> findByRegistrationNo(String registrationNo);

    boolean existsByBusNo(String busNo);

    boolean existsByRegistrationNo(String registrationNo);
}
