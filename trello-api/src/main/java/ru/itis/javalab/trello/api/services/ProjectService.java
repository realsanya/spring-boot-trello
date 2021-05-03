package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectService <ProjectDto, Long> {
    Page<ProjectDto> getAllProjectsByUserId(Long userId, Pageable pageable);
    Page<ProjectDto> getAll(Pageable pageable);
    Optional<ProjectDto> getById(Long id);
}
