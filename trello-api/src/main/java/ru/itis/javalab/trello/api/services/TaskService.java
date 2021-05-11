package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService<TaskDto, Long> {
    Page<TaskDto> getByDashboardId(Long dashboardId, Pageable pageable);
    Optional<TaskDto> getById(Long id);
    void addTask(TaskDto taskDto);
    void archiveTask(Long id);
}
