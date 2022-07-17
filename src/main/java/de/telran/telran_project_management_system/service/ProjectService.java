package de.telran.telran_project_management_system.service;

import de.telran.telran_project_management_system.dto.ProjectRequestDTO;
import de.telran.telran_project_management_system.dto.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    void createProject(ProjectRequestDTO request);

    List<ProjectResponseDTO> getAllProjects(String name);
}
