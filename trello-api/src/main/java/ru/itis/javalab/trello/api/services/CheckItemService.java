package ru.itis.javalab.trello.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CheckItemService<CheckItemDto, Long> {
    Page<CheckItemDto> getAllByTaskId(Long taskId, Pageable pageable);
}
