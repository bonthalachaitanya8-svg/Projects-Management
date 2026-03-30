package com.chaitanya.projectmanagement.response;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private Long ownerId;
    private String ownerName;
    private List<ProjectFileResponse> files;
    private List<ProjectLinkResponse> links;

    public ProjectResponse() {}

    public ProjectResponse(Long id, String title, String description, String status, LocalDate startDate, LocalDate endDate, boolean active, Long ownerId, String ownerName, List<ProjectFileResponse> files, List<ProjectLinkResponse> links) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.files = files;
        this.links = links;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isActive() { return active; }
    public Long getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
    public List<ProjectFileResponse> getFiles() { return files; }
    public List<ProjectLinkResponse> getLinks() { return links; }
}
