package de.telran.telran_project_management_system.repository;

import de.telran.telran_project_management_system.entity.Project;
import de.telran.telran_project_management_system.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);

    List<Task> findAllByProjectAndTaskNameIgnoreCaseContaining(Project project, String name);
}
