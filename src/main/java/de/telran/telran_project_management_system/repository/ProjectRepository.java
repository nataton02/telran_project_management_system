package de.telran.telran_project_management_system.repository;

import de.telran.telran_project_management_system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByProjectNameIgnoreCaseContaining(String name);
}
