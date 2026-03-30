package com.chaitanya.projectmanagement.repository;

import com.chaitanya.projectmanagement.entity.ProjectFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectFileRepository extends JpaRepository<ProjectFileEntity, Long> {
}
