package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.dto.LoginRequest;
import com.busfleetmanagement.system.dto.LoginResponse;
import com.busfleetmanagement.system.entity.Owner;
import com.busfleetmanagement.system.repository.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final OwnerRepository ownerRepository;

    public AuthService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Owner owner = ownerRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (owner == null) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!owner.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return new LoginResponse(
                "Login successful",
                owner.getOwnerId(),
                owner.getUsername(),
                owner.getName()
        );
    }
}