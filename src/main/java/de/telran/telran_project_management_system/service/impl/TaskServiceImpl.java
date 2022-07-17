package de.telran.telran_project_management_system.service.impl;

import de.telran.telran_project_management_system.dto.TaskRequestDTO;
import de.telran.telran_project_management_system.dto.TaskResponseDTO;
import de.telran.telran_project_management_system.entity.Project;
import de.telran.telran_project_management_system.entity.Task;
import de.telran.telran_project_management_system.entity.types.TaskStatus;
import de.telran.telran_project_management_system.repository.ProjectRepository;
import de.telran.telran_project_management_system.repository.TaskRepository;
import de.telran.telran_project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;


    @Override
    public void createTask(TaskRequestDTO request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Project with id %s does not exist", request.getProjectId())));


        Task task = convertDtoToTask(request, project);
        task.setTaskStatus(TaskStatus.getByStatusId(1));
        taskRepository.save(task);
    }

    @Override
    public void pushTaskForward(Long taskId) {
        Task task = getById(taskId);

        if(task.getTaskStatus().equals(TaskStatus.DONE)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This task is already done and cannot be pushed forward");
        }

        if(task.getTaskStatus().equals(TaskStatus.IN_PROGRESS)) {
            task.setTaskStatus(TaskStatus.DONE);
        }

        if(task.getTaskStatus().equals(TaskStatus.TODO)) {
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
        }

        taskRepository.save(task);
    }

    @Override
    public void pushTaskBackwards(Long taskId) {
        Task task = getById(taskId);

        if(task.getTaskStatus().equals(TaskStatus.TODO)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This task is already in todo state and cannot be pushed backwards");
        }

        if(task.getTaskStatus().equals(TaskStatus.IN_PROGRESS)) {
            task.setTaskStatus(TaskStatus.TODO);
        }

        if(task.getTaskStatus().equals(TaskStatus.DONE)) {
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
        }

        taskRepository.save(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasksByProject(Long projectId, String name) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Project with id %s does not exist", projectId)));
        List<Task> tasks;
        if(name == null) {
            tasks = taskRepository.findAllByProject(project);
        }
        else{
            tasks = taskRepository.findAllByProjectAndTaskNameIgnoreCaseContaining(project, name);
        }

        return  tasks.stream()
                .map(this::convertTaskToDto)
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

    private Task getById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("Task with id %s does not exist", taskId)));
    }

    private Task convertDtoToTask(TaskRequestDTO request, Project project) {
        return Task.builder()
                .taskName(request.getTaskName())
                .daysToComplete(request.getDaysToComplete())
                .project(project)
                .build();
    }
}
