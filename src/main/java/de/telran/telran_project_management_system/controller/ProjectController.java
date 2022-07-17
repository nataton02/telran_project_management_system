package de.telran.telran_project_management_system.controller;

import de.telran.telran_project_management_system.dto.ProjectRequestDTO;
import de.telran.telran_project_management_system.dto.ProjectResponseDTO;
import de.telran.telran_project_management_system.dto.TaskRequestDTO;
import de.telran.telran_project_management_system.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/api/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody @Valid ProjectRequestDTO request) {
        projectService.createProject(request);
    }

    @GetMapping("/api/projects")
    public List<ProjectResponseDTO> getAllProjects(
            @RequestParam(name = "name", required = false) String name) {
        return projectService.getAllProjects(name);
    }



}
