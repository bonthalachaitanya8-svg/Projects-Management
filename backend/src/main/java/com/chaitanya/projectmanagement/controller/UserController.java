package com.chaitanya.projectmanagement.controller;

import com.chaitanya.projectmanagement.dto.UpdateProfileRequest;
import com.chaitanya.projectmanagement.entity.UserEntity;
import com.chaitanya.projectmanagement.response.UserResponse;
import com.chaitanya.projectmanagement.service.DatabaseProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final DatabaseProjectService service;

    public UserController(DatabaseProjectService service) {
        this.service = service;
    }

    private UserEntity currentUser(String authHeader) {
        String token = authHeader == null ? null : authHeader.replace("Bearer ", "");
        return service.authenticate(token);
    }

    @GetMapping
    public List<UserResponse> getUsers(@RequestHeader("Authorization") String authHeader) {
        return service.getUsers(currentUser(authHeader));
    }

    @GetMapping("/me")
    public UserResponse me(@RequestHeader("Authorization") String authHeader) {
        return service.getMyProfile(currentUser(authHeader));
    }

    @PutMapping("/me")
    public UserResponse updateMe(@RequestHeader("Authorization") String authHeader,
                                 @RequestBody UpdateProfileRequest request) {
        return service.updateMyProfile(currentUser(authHeader), request);
    }
}
