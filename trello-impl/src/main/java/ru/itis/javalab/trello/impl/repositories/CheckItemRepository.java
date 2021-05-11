package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.CheckItem;

public interface CheckItemRepository extends JpaRepository<CheckItem, Long> {
    @EntityGraph(value = "Task.CheckItems")
    Page<CheckItem> findByTaskId(Long taskId, Pageable pageable);
}
