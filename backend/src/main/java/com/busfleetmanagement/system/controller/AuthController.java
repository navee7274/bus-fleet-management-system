package com.busfleetmanagement.system.controller;

import com.busfleetmanagement.system.dto.LoginRequest;
import com.busfleetmanagement.system.dto.LoginResponse;
import com.busfleetmanagement.system.entity.Owner;
import com.busfleetmanagement.system.repository.OwnerRepository;
import com.busfleetmanagement.system.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class AuthController {

    private final AuthService authService;
    private final OwnerRepository ownerRepository;

    public AuthController(
            AuthService authService,
            OwnerRepository ownerRepository
    ) {
        this.authService = authService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.login(request);

        Cookie cookie = new Cookie(
                "SESSION_ID",
                loginResponse.getSessionId()
        );

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(
            HttpServletRequest request
    ) {
        Integer ownerId = (Integer) request.getAttribute("ownerId");

        if (ownerId == null) {
            return ResponseEntity.status(401).build();
        }

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return ResponseEntity.ok(
                new LoginResponse(
                        "Authenticated",
                        owner.getOwnerId(),
                        owner.getUsername(),
                        owner.getName()
                )
        );
    }
}