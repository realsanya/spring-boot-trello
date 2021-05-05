package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.Dashboard;
import ru.itis.javalab.trello.impl.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Dashboard> findByDashboardId(Long dashboardId, Pageable pageable);
}
