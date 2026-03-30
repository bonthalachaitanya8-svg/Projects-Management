package com.chaitanya.projectmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "project_links")
public class ProjectLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1200)
    private String githubUrl;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    public ProjectLinkEntity() {}

    public ProjectLinkEntity(String githubUrl, String description, ProjectEntity project) {
        this.githubUrl = githubUrl;
        this.description = description;
        this.project = project;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ProjectEntity getProject() { return project; }
    public void setProject(ProjectEntity project) { this.project = project; }
}
