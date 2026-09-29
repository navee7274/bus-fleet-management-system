package com.busfleetmanagement.system.service;

import com.busfleetmanagement.system.dto.LoginRequest;
import com.busfleetmanagement.system.dto.LoginResponse;
import com.busfleetmanagement.system.entity.Owner;
import com.busfleetmanagement.system.repository.OwnerRepository;
import com.busfleetmanagement.system.security.SessionManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final OwnerRepository ownerRepository;
    private final SessionManager sessionManager;

    public AuthService(
            OwnerRepository ownerRepository,
            SessionManager sessionManager
    ) {
        this.ownerRepository = ownerRepository;
        this.sessionManager = sessionManager;
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

        // Create server-side session
        String sessionId = sessionManager.createSession(owner.getOwnerId());

        return new LoginResponse(
                "Login successful",
                owner.getOwnerId(),
                owner.getUsername(),
                owner.getName(),
                sessionId
        );
    }
}