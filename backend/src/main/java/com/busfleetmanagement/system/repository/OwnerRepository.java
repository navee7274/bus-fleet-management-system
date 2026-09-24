package com.busfleetmanagement.system.repository;

import com.busfleetmanagement.system.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer>{
    Optional<Owner> findByUsername(String username);
}
