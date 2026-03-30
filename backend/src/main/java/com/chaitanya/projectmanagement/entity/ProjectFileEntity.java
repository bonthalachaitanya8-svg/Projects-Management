package com.chaitanya.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_files")
public class ProjectFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, length = 1200)
    private String fileUrl;

    private String fileType;
    private String folderName;
    private String relativePath;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_id", nullable = false)
    private UserEntity uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    public ProjectFileEntity() {}

    public ProjectFileEntity(String fileName, String fileUrl, String fileType, String folderName, String relativePath, LocalDateTime uploadedAt, UserEntity uploadedBy, ProjectEntity project) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.folderName = folderName;
        this.relativePath = relativePath;
        this.uploadedAt = uploadedAt;
        this.uploadedBy = uploadedBy;
        this.project = project;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getFolderName() { return folderName; }
    public void setFolderName(String folderName) { this.folderName = folderName; }

    public String getRelativePath() { return relativePath; }
    public void setRelativePath(String relativePath) { this.relativePath = relativePath; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public UserEntity getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(UserEntity uploadedBy) { this.uploadedBy = uploadedBy; }

    public ProjectEntity getProject() { return project; }
    public void setProject(ProjectEntity project) { this.project = project; }
}
