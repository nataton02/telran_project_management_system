package de.telran.telran_project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputValidationResponseDTO {

    private String message;
    private HttpStatus status;
    private Map<String, List<String >> errors;
}
