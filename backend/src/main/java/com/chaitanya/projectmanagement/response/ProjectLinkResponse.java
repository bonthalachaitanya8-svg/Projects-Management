package com.chaitanya.projectmanagement.response;

public class ProjectLinkResponse {
    private Long id;
    private String githubUrl;
    private String description;

    public ProjectLinkResponse() {}

    public ProjectLinkResponse(Long id, String githubUrl, String description) {
        this.id = id;
        this.githubUrl = githubUrl;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getGithubUrl() { return githubUrl; }
    public String getDescription() { return description; }
}
