package com.chaitanya.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateProjectLinkRequest {
    @NotBlank
    private String githubUrl;

    private String description;

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
