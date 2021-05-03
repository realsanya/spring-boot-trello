package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.Project;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByUserId(Long userId, Pageable pageable);
}
