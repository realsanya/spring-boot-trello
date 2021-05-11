package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.services.CommentService;
import ru.itis.javalab.trello.impl.models.Comment;
import ru.itis.javalab.trello.impl.models.Task;
import ru.itis.javalab.trello.impl.repositories.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService<CommentDto, Long> {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CommentDto> getByTaskId(Long taskId, Pageable pageable) {
        return commentRepository.findByTaskId(taskId, pageable).map(
                comment -> modelMapper.map(comment, CommentDto.class));
    }

    @Override
    public void addComment(CommentDto commentDto) {
        commentRepository.save(modelMapper.map(commentDto, Comment.class));
    }
}
