package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.dto.LoginRequest;
import com.busfleetmanagement.system.dto.LoginResponse;
import com.busfleetmanagement.system.entity.Owner;
import com.busfleetmanagement.system.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final OwnerRepository ownerRepository;

    public AuthService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Optional<Owner> ownerOptional =
                ownerRepository.findByUsername(request.getUsername());

        if (ownerOptional.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        Owner owner = ownerOptional.get();

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