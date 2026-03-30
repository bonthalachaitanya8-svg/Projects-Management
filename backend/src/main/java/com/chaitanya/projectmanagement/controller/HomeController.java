package com.chaitanya.projectmanagement.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of("message", "Project Management API is running", "status", "success");
    }
}
