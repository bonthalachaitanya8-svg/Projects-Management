package com.chaitanya.projectmanagement.service;

import com.chaitanya.projectmanagement.dto.*;
import com.chaitanya.projectmanagement.entity.*;
import com.chaitanya.projectmanagement.model.ProjectStatus;
import com.chaitanya.projectmanagement.model.Role;
import com.chaitanya.projectmanagement.repository.*;
import com.chaitanya.projectmanagement.response.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DatabaseProjectService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectFileRepository projectFileRepository;
    private final ProjectLinkRepository projectLinkRepository;
    private final Map<String, Long> tokenToUserId = new ConcurrentHashMap<>();

    public DatabaseProjectService(UserRepository userRepository,
                                  ProjectRepository projectRepository,
                                  ProjectFileRepository projectFileRepository,
                                  ProjectLinkRepository projectLinkRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectFileRepository = projectFileRepository;
        this.projectLinkRepository = projectLinkRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserEntity admin = userRepository.save(new UserEntity(
                    "Chaitanya Admin",
                    "admin@example.com",
                    "Password@123",
                    "Java, Spring Boot, GitHub",
                    "Admin user for project visibility",
                    Role.ADMIN
            ));

            UserEntity user = userRepository.save(new UserEntity(
                    "Chaitanya User",
                    "user@example.com",
                    "Password@123",
                    "React, Java",
                    "Standard user profile",
                    Role.USER
            ));

            ProjectEntity first = projectRepository.save(new ProjectEntity(
                    "Project Management Portal",
                    "Starter project with admin and user views",
                    ProjectStatus.IN_PROGRESS,
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(30),
                    true,
                    user
            ));

            ProjectEntity second = projectRepository.save(new ProjectEntity(
                    "Admin Analytics Dashboard",
                    "Dashboard cards and reporting widgets",
                    ProjectStatus.PLANNING,
                    LocalDate.now(),
                    LocalDate.now().plusDays(14),
                    true,
                    admin
            ));

            projectFileRepository.save(new ProjectFileEntity(
                    "requirements.pdf",
                    "https://example.com/requirements.pdf",
                    "PDF",
                    "project-management",
                    "docs/requirements.pdf",
                    LocalDateTime.now(),
                    user,
                    first
            ));

            projectLinkRepository.save(new ProjectLinkEntity(
                    "https://github.com/example/project-management",
                    "Frontend and backend starter",
                    first
            ));

            projectRepository.save(second);
        }
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .filter(found -> Objects.equals(found.getPassword(), request.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        String token = UUID.randomUUID().toString();
        tokenToUserId.put(token, user.getId());

        return new LoginResponse(token, user.getId(), user.getFullName(), user.getEmail(), user.getRole().name());
    }

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = userRepository.save(new UserEntity(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getSkills(),
                request.getProfileSummary(),
                Role.USER
        ));

        String token = UUID.randomUUID().toString();
        tokenToUserId.put(token, user.getId());

        return new LoginResponse(token, user.getId(), user.getFullName(), user.getEmail(), user.getRole().name());
    }

    public UserEntity authenticate(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Unauthorized");
        }

        Long userId = tokenToUserId.get(token);
        if (userId == null) {
            throw new RuntimeException("Unauthorized");
        }

        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Unauthorized"));
    }

    public List<UserResponse> getUsers(UserEntity currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admins can view all users");
        }

        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .toList();
    }

    public UserResponse getMyProfile(UserEntity currentUser) {
        return toUserResponse(currentUser);
    }

    public UserResponse updateMyProfile(UserEntity currentUser, UpdateProfileRequest request) {
        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            currentUser.setFullName(request.getFullName());
        }
        if (request.getSkills() != null) {
            currentUser.setSkills(request.getSkills());
        }
        if (request.getProfileSummary() != null) {
            currentUser.setProfileSummary(request.getProfileSummary());
        }

        UserEntity saved = userRepository.save(currentUser);

        List<ProjectEntity> ownedProjects = projectRepository.findByOwnerIdOrderByIdDesc(saved.getId());
        for (ProjectEntity project : ownedProjects) {
            project.setOwner(saved);
        }
        projectRepository.saveAll(ownedProjects);

        return toUserResponse(saved);
    }

    public List<ProjectResponse> getProjects(UserEntity currentUser) {
        List<ProjectEntity> projects = currentUser.getRole() == Role.ADMIN
                ? projectRepository.findAllByOrderByIdDesc()
                : projectRepository.findByOwnerIdOrderByIdDesc(currentUser.getId());

        return projects.stream().map(this::toProjectResponse).toList();
    }

    public ProjectResponse createProject(UserEntity currentUser, CreateProjectRequest request) {
        UserEntity owner = currentUser;

        if (currentUser.getRole() == Role.ADMIN && request.getOwnerId() != null) {
            owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
        }

        ProjectEntity entity = new ProjectEntity(
                request.getTitle(),
                request.getDescription(),
                request.getStatus() == null ? ProjectStatus.PLANNING : request.getStatus(),
                request.getStartDate(),
                request.getEndDate(),
                true,
                owner
        );

        return toProjectResponse(projectRepository.save(entity));
    }

    public ProjectResponse updateProject(UserEntity currentUser, Long id, UpdateProjectRequest request) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean canEdit = currentUser.getRole() == Role.ADMIN || Objects.equals(project.getOwner().getId(), currentUser.getId());
        if (!canEdit) {
            throw new RuntimeException("You can edit only your own project");
        }

        if (request.getTitle() != null) project.setTitle(request.getTitle());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStatus() != null) project.setStatus(request.getStatus());
        if (request.getStartDate() != null) project.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) project.setEndDate(request.getEndDate());
        if (request.getActive() != null) project.setActive(request.getActive());

        return toProjectResponse(projectRepository.save(project));
    }

    public ProjectFileResponse addProjectFile(UserEntity currentUser, Long projectId, CreateProjectFileRequest request) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean canEdit = currentUser.getRole() == Role.ADMIN || Objects.equals(project.getOwner().getId(), currentUser.getId());
        if (!canEdit) {
            throw new RuntimeException("You can upload files only to your own project");
        }

        ProjectFileEntity file = new ProjectFileEntity(
                request.getFileName(),
                request.getFileUrl(),
                request.getFileType(),
                request.getFolderName(),
                request.getRelativePath(),
                LocalDateTime.now(),
                currentUser,
                project
        );

        return toProjectFileResponse(projectFileRepository.save(file));
    }

    public ProjectLinkResponse addProjectLink(UserEntity currentUser, Long projectId, CreateProjectLinkRequest request) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        boolean canEdit = currentUser.getRole() == Role.ADMIN || Objects.equals(project.getOwner().getId(), currentUser.getId());
        if (!canEdit) {
            throw new RuntimeException("You can add links only to your own project");
        }

        ProjectLinkEntity link = new ProjectLinkEntity(
                request.getGithubUrl(),
                request.getDescription(),
                project
        );

        return toProjectLinkResponse(projectLinkRepository.save(link));
    }

    public Map<String, Object> dashboard(UserEntity currentUser) {
        List<ProjectEntity> visibleProjects = currentUser.getRole() == Role.ADMIN
                ? projectRepository.findAllByOrderByIdDesc()
                : projectRepository.findByOwnerIdOrderByIdDesc(currentUser.getId());

        long totalFiles = visibleProjects.stream().mapToLong(project -> project.getFiles().size()).sum();
        long totalLinks = visibleProjects.stream().mapToLong(project -> project.getLinks().size()).sum();
        long activeProjects = visibleProjects.stream().filter(ProjectEntity::isActive).count();
        long completedProjects = visibleProjects.stream().filter(project -> project.getStatus() == ProjectStatus.COMPLETED).count();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalUsers", currentUser.getRole() == Role.ADMIN ? userRepository.count() : 1);
        map.put("totalProjects", visibleProjects.size());
        map.put("totalFiles", totalFiles);
        map.put("totalLinks", totalLinks);
        map.put("activeProjects", activeProjects);
        map.put("completedProjects", completedProjects);
        return map;
    }

    private UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getSkills(),
                user.getProfileSummary(),
                user.getRole().name()
        );
    }

    private ProjectResponse toProjectResponse(ProjectEntity project) {
        List<ProjectFileResponse> files = project.getFiles().stream()
                .map(this::toProjectFileResponse)
                .toList();

        List<ProjectLinkResponse> links = project.getLinks().stream()
                .map(this::toProjectLinkResponse)
                .toList();

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus().name(),
                project.getStartDate(),
                project.getEndDate(),
                project.isActive(),
                project.getOwner().getId(),
                project.getOwner().getFullName(),
                files,
                links
        );
    }

    private ProjectFileResponse toProjectFileResponse(ProjectFileEntity file) {
        return new ProjectFileResponse(
                file.getId(),
                file.getFileName(),
                file.getFileUrl(),
                file.getFileType(),
                file.getFolderName(),
                file.getRelativePath()
        );
    }

    private ProjectLinkResponse toProjectLinkResponse(ProjectLinkEntity link) {
        return new ProjectLinkResponse(
                link.getId(),
                link.getGithubUrl(),
                link.getDescription()
        );
    }
}
