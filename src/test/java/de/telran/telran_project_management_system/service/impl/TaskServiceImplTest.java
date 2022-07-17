package de.telran.telran_project_management_system.service.impl;

import de.telran.telran_project_management_system.dto.TaskRequestDTO;
import de.telran.telran_project_management_system.entity.Project;
import de.telran.telran_project_management_system.entity.Task;
import de.telran.telran_project_management_system.entity.types.TaskStatus;
import de.telran.telran_project_management_system.repository.ProjectRepository;
import de.telran.telran_project_management_system.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    @DisplayName("should throw 404-NOT_FOUND, when no such project")
    public void shouldThrow404WhenNoSuchProject() {
        TaskRequestDTO request = TaskRequestDTO.builder()
                .taskName("Task1")
                .projectId(1L)
                .build();

        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("Project with id %s does not exist", request.getProjectId());

        Mockito
                .when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.createTask(request)
        );

        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }

    @Test
    @DisplayName("should save() task, when there is such project")
    public void shouldSaveTaskWhenThereIsSuchProjectTest() {

        TaskRequestDTO request = TaskRequestDTO.builder()
                .taskName("Task1")
                .projectId(1L)
                .daysToComplete(10)
                .build();

        Project project = Project.builder()
                .projectId(request.getProjectId())
                .projectName("Project1")
                .build();

        Task task = Task.builder()
                .taskId(1L)
                .taskName(request.getTaskName())
                .project(project)
                .daysToComplete(request.getDaysToComplete())
                .build();
        task.setTaskStatus(TaskStatus.TODO);

        Mockito
                .when(projectRepository.findById(request.getProjectId()))
                .thenReturn(Optional.of(project));

        Mockito
                .when(taskRepository.save(ArgumentMatchers.argThat(
                        savedTask -> {
                            return savedTask.getTaskName().equals(request.getTaskName()) &&
                                    savedTask.getProject().getProjectId().equals(project.getProjectId()) &&
                                    savedTask.getProject().getProjectName().equals(project.getProjectName()) &&
                                    savedTask.getDaysToComplete().equals(request.getDaysToComplete()) &&
                                    savedTask.getTaskStatus().getTaskStatusId().equals(1);
                        })))
                .thenReturn(task);

        service.createTask(request);
    }

    @Test
    @DisplayName("should throw 404-NOT_FOUND, when no such task")
    public void shouldThrow404WhenNoSuchTask() {
        Long taskId = 1L;

        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = String.format("Task with id %s does not exist", taskId);

        Mockito
                .when(taskRepository.findById(taskId))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = Assertions.assertThrows(
                ResponseStatusException.class,
                () -> service.pushTaskForward(taskId)
        );

        Assertions.assertEquals(expectedStatus, ex.getStatus());
        Assertions.assertEquals(expectedMessage, ex.getReason());
    }

    @Test
    @DisplayName("should return DTO, when there is such task")
    public void shouldReturnDtoWhenThereIsSuchTask() {
        Long taskId = 1L;

        Task task = Task.builder()
                .taskId(1L)
                .taskName("Task1")
                .daysToComplete(5)
                .project(Project.builder()
                        .projectId(1L)
                        .projectName("Project1")
                        .build())
                .taskStatus(TaskStatus.TODO)
                .build();

        Mockito.when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));


    }



}
