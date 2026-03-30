package com.chaitanya.projectmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateProjectFileRequest {
    @NotBlank
    private String fileName;

    @NotBlank
    private String fileUrl;

    private String fileType;
    private String folderName;
    private String relativePath;

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
}
