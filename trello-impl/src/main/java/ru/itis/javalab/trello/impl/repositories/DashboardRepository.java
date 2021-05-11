package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.Dashboard;

import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    Page<Dashboard> findByProjectId(Long projectId, Pageable pageable);
    @EntityGraph(value = "Dashboard.Tasks")
    Optional<Dashboard> findByProjectIdAndUserId(Long projectId, Long userId);
}
