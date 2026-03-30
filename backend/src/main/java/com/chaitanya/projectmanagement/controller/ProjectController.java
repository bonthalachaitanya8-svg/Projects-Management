package com.chaitanya.projectmanagement.controller;

import com.chaitanya.projectmanagement.dto.*;
import com.chaitanya.projectmanagement.entity.UserEntity;
import com.chaitanya.projectmanagement.response.ProjectFileResponse;
import com.chaitanya.projectmanagement.response.ProjectLinkResponse;
import com.chaitanya.projectmanagement.response.ProjectResponse;
import com.chaitanya.projectmanagement.service.DatabaseProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    private final DatabaseProjectService service;

    public ProjectController(DatabaseProjectService service) {
        this.service = service;
    }

    private UserEntity currentUser(String authHeader) {
        String token = authHeader == null ? null : authHeader.replace("Bearer ", "");
        return service.authenticate(token);
    }

    @GetMapping
    public List<ProjectResponse> getProjects(@RequestHeader("Authorization") String authHeader) {
        return service.getProjects(currentUser(authHeader));
    }

    @PostMapping
    public ProjectResponse createProject(@RequestHeader("Authorization") String authHeader,
                                         @Valid @RequestBody CreateProjectRequest request) {
        return service.createProject(currentUser(authHeader), request);
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(@RequestHeader("Authorization") String authHeader,
                                         @PathVariable Long id,
                                         @RequestBody UpdateProjectRequest request) {
        return service.updateProject(currentUser(authHeader), id, request);
    }

    @PostMapping("/{id}/files")
    public ProjectFileResponse addFile(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id,
                                       @Valid @RequestBody CreateProjectFileRequest request) {
        return service.addProjectFile(currentUser(authHeader), id, request);
    }

    @PostMapping("/{id}/links")
    public ProjectLinkResponse addLink(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id,
                                       @Valid @RequestBody CreateProjectLinkRequest request) {
        return service.addProjectLink(currentUser(authHeader), id, request);
    }
}
