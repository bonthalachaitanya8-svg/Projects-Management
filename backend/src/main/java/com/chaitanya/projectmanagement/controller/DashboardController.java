package com.chaitanya.projectmanagement.controller;

import com.chaitanya.projectmanagement.entity.UserEntity;
import com.chaitanya.projectmanagement.service.DatabaseProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DatabaseProjectService service;

    public DashboardController(DatabaseProjectService service) {
        this.service = service;
    }

    private UserEntity currentUser(String authHeader) {
        String token = authHeader == null ? null : authHeader.replace("Bearer ", "");
        return service.authenticate(token);
    }

    @GetMapping
    public Map<String, Object> dashboard(@RequestHeader("Authorization") String authHeader) {
        return service.dashboard(currentUser(authHeader));
    }
}
