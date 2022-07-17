package de.telran.telran_project_management_system.entity;

import de.telran.telran_project_management_system.entity.types.TaskStatus;
import de.telran.telran_project_management_system.entity.types.TaskStatusConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "days_to_complete")
    private Integer daysToComplete;

    @Column(name = "task_status")
    @Convert(converter = TaskStatusConverter.class)
    private TaskStatus taskStatus;

    @Column(name = "created_on")
    @CreatedDate
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    private LocalDateTime updatedOn;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

}
