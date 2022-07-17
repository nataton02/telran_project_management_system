package de.telran.telran_project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDTO {

    private Long projectId;

    private String projectName;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private List<TaskResponseDTO> tasks;

}
