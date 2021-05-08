package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.dto.TaskDto;
import ru.itis.javalab.trello.api.services.CommentService;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Получение комментариев по id задачи")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = CommentDto.class)})
    @GetMapping("/comments")
    public Page<TaskDto> findAllByTaskID(@RequestParam Long taskId, Pageable pageable){
        return commentService.getByTaskId(taskId, pageable);
    }


    @ApiOperation(value = "Добавление нового комментария")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно сохранен", response = CommentDto.class)})
    @PostMapping("/comments/add")
    public ResponseEntity<?> addTask(@RequestBody CommentDto commentDto){
        commentService.addComment(commentDto);
        return ResponseEntity.ok("Success");
    }
}
