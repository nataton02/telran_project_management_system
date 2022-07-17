package de.telran.telran_project_management_system.dto;

import de.telran.telran_project_management_system.entity.types.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDTO {

    @NotBlank
    @Length(min = 5, max = 30)
    private String taskName;

    @NotNull
    @Min(value = 1)
    @Max(value = 364)
    private Integer daysToComplete;

    @NotNull
    private Long projectId;
}
