package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @EntityGraph(value = "Task.Attachments")
    Page<Attachment> findByTaskId(Long taskId, Pageable pageable);
}
