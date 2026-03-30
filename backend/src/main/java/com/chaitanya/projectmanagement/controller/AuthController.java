package com.chaitanya.projectmanagement.controller;

import com.chaitanya.projectmanagement.dto.LoginRequest;
import com.chaitanya.projectmanagement.dto.LoginResponse;
import com.chaitanya.projectmanagement.dto.RegisterRequest;
import com.chaitanya.projectmanagement.service.DatabaseProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final DatabaseProjectService service;

    public AuthController(DatabaseProjectService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }
}
