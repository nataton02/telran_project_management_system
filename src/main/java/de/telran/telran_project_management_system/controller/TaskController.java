package de.telran.telran_project_management_system.controller;

import de.telran.telran_project_management_system.dto.ProjectResponseDTO;
import de.telran.telran_project_management_system.dto.TaskRequestDTO;
import de.telran.telran_project_management_system.dto.TaskResponseDTO;
import de.telran.telran_project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskRequestDTO request) {
        taskService.createTask(request);
    }

    @PutMapping("/api/tasks/{taskId}/forward")
    public void updateTaskForward(@Valid @PathVariable("taskId") Long taskId) {
        taskService.pushTaskForward(taskId);
    }

    @PutMapping("/api/tasks/{taskId}/backwards")
    public void updateTaskBackwards(@Valid @PathVariable("taskId") Long taskId) {
        taskService.pushTaskBackwards(taskId);
    }

    @GetMapping("/api/projects/{projectId}/tasks")
    public List<TaskResponseDTO> getAllTasks(
            @PathVariable("projectId") Long projectId,
            @RequestParam(name = "name", required = false) String name) {
        return taskService.getAllTasksByProject(projectId, name);
    }
}
