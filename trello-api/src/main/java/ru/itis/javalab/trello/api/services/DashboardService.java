package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DashboardService <DashboardDto, Long> {
    Page<DashboardDto> getByProjectId(Long projectId, Pageable pageable);
    Optional<DashboardDto> getByProjectIdAndUserId(Long projectId, Long userId);
}
