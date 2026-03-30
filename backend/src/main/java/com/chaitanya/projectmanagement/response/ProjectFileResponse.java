package com.chaitanya.projectmanagement.response;

public class ProjectFileResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String folderName;
    private String relativePath;

    public ProjectFileResponse() {}

    public ProjectFileResponse(Long id, String fileName, String fileUrl, String fileType, String folderName, String relativePath) {
        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.folderName = folderName;
        this.relativePath = relativePath;
    }

    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public String getFileUrl() { return fileUrl; }
    public String getFileType() { return fileType; }
    public String getFolderName() { return folderName; }
    public String getRelativePath() { return relativePath; }
}
