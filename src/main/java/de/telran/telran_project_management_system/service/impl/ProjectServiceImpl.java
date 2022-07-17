package de.telran.telran_project_management_system.service.impl;

import de.telran.telran_project_management_system.dto.ProjectRequestDTO;
import de.telran.telran_project_management_system.dto.ProjectResponseDTO;
import de.telran.telran_project_management_system.dto.TaskResponseDTO;
import de.telran.telran_project_management_system.entity.Project;
import de.telran.telran_project_management_system.entity.Task;
import de.telran.telran_project_management_system.repository.ProjectRepository;
import de.telran.telran_project_management_system.repository.TaskRepository;
import de.telran.telran_project_management_system.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;


    @Override
    public void createProject(ProjectRequestDTO request) {
        Project project = Project.builder()
                .projectName(request.getProjectName())
                .build();
        projectRepository.save(project);

    }

    @Override
    public List<ProjectResponseDTO> getAllProjects(String name) {
        List<Project> projects;

        if(name == null) {
            projects = projectRepository.findAll();
        }

        else {
            projects = projectRepository.findAllByProjectNameIgnoreCaseContaining(name);
        }

        return projects.stream()
                .map(project -> {
                    List<Task> tasks = taskRepository.findAllByProject(project);
                    List<TaskResponseDTO> tasksDTO = tasks.stream()
                            .map(this::convertTaskToDto)
                            .collect(Collectors.toList());

                    return convertProjectToDto(project, tasksDTO);
                })
                .collect(Collectors.toList());
    }

    private TaskResponseDTO convertTaskToDto(Task task) {
        return TaskResponseDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .daysToComplete(task.getDaysToComplete())
                .taskStatus(task.getTaskStatus())
                .createdOn(task.getCreatedOn())
                .updatedOn(task.getUpdatedOn())
                .build();
    }

    private ProjectResponseDTO convertProjectToDto(Project project, List<TaskResponseDTO> tasksDTO) {
        return ProjectResponseDTO.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .createdOn(project.getCreatedOn())
                .updatedOn(project.getUpdatedOn())
                .tasks(tasksDTO)
                .build();
    }
}
