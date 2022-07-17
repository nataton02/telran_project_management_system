package de.telran.telran_project_management_system.dto;

import de.telran.telran_project_management_system.entity.types.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDTO {

    private Long taskId;

    private String taskName;

    private Integer daysToComplete;

    private TaskStatus taskStatus;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}
