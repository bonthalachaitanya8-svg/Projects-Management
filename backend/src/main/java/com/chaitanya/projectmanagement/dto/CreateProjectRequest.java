package com.chaitanya.projectmanagement.dto;

import com.chaitanya.projectmanagement.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CreateProjectRequest {
    @NotBlank
    private String title;

    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long ownerId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}
