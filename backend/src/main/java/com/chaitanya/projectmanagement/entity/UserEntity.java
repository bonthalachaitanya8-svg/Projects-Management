package com.chaitanya.projectmanagement.entity;

import com.chaitanya.projectmanagement.model.Role;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String skills;

    @Column(length = 3000)
    private String profileSummary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<ProjectEntity> projects = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(String fullName, String email, String password, String skills, String profileSummary, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.skills = skills;
        this.profileSummary = profileSummary;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getProfileSummary() { return profileSummary; }
    public void setProfileSummary(String profileSummary) { this.profileSummary = profileSummary; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<ProjectEntity> getProjects() { return projects; }
    public void setProjects(List<ProjectEntity> projects) { this.projects = projects; }
}
