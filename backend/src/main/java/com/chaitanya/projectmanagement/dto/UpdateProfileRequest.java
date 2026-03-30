package com.chaitanya.projectmanagement.dto;

public class UpdateProfileRequest {
    private String fullName;
    private String skills;
    private String profileSummary;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getProfileSummary() { return profileSummary; }
    public void setProfileSummary(String profileSummary) { this.profileSummary = profileSummary; }
}
