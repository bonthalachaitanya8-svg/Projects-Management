package com.chaitanya.projectmanagement.repository;

import com.chaitanya.projectmanagement.entity.ProjectEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @EntityGraph(attributePaths = {"owner", "files", "links"})
    List<ProjectEntity> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = {"owner", "files", "links"})
    List<ProjectEntity> findByOwnerIdOrderByIdDesc(Long ownerId);
}
