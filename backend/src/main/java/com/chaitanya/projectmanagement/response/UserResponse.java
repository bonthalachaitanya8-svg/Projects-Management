package com.chaitanya.projectmanagement.response;

public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String skills;
    private String profileSummary;
    private String role;

    public UserResponse() {}

    public UserResponse(Long id, String fullName, String email, String skills, String profileSummary, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.skills = skills;
        this.profileSummary = profileSummary;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getSkills() { return skills; }
    public String getProfileSummary() { return profileSummary; }
    public String getRole() { return role; }
}
