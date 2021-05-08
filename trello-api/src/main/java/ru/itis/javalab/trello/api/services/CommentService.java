package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService <CommentDto, Long> {
    Page<CommentDto> getByTaskId(Long taskId, Pageable pageable);
    void addComment(CommentDto commentDto);
}
