package de.telran.telran_project_management_system.entity.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TaskStatus {

    TODO(1, "todo"),
    IN_PROGRESS(2, "in progress"),
    DONE(3, "done")
    ;

    private final Integer taskStatusId;
    private final String externalStatusId;

    public static TaskStatus getByStatusId(Integer statusId) {
        if(statusId == null) {
            return null;
        }

        return Arrays.stream(TaskStatus.values())
                .filter(x -> x.getTaskStatusId().equals(statusId))
                .findFirst()
                .orElse(null);
    }

    @JsonCreator
    public static TaskStatus findByExternalStatusId(String statusId) {
        if(statusId == null) {
            return null;
        }

        return  Arrays.stream(TaskStatus.values())
                .filter(x -> x.getExternalStatusId().equals(statusId))
                .findFirst()
                .orElse(null);
    }


}
