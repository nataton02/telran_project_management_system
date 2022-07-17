package de.telran.telran_project_management_system.service;

import de.telran.telran_project_management_system.dto.TaskRequestDTO;
import de.telran.telran_project_management_system.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    void createTask(TaskRequestDTO request);

    void pushTaskForward(Long taskId);

    void pushTaskBackwards(Long taskId);

    List<TaskResponseDTO> getAllTasksByProject(Long projectId, String name);
}
