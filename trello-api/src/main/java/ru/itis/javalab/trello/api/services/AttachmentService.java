package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttachmentService <AttachmentDto, Long>{
    Page<AttachmentDto> getByTaskId(Long taskId, Pageable pageable);
}
