package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.CheckItemDto;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.services.CheckItemService;
import ru.itis.javalab.trello.impl.repositories.CheckItemRepository;

@Service
public class CheckItemServiceImpl implements CheckItemService<CheckItemDto, Long> {

    private final CheckItemRepository checkItemRepository;
    private final ModelMapper modelMapper;

    public CheckItemServiceImpl(CheckItemRepository checkItemRepository, ModelMapper modelMapper) {
        this.checkItemRepository = checkItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CheckItemDto> getAllByTaskId(Long taskId, Pageable pageable) {
        return checkItemRepository.findByTaskId(taskId, pageable).map(
                checkItem -> modelMapper.map(checkItem, CheckItemDto.class));
    }
}
