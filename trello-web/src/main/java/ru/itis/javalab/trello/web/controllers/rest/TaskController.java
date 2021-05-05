package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.dto.TaskDto;
import ru.itis.javalab.trello.api.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Создание новой задачи")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно сохранен", response = TaskDto.class)})
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskDto taskDto){
        taskService.addTask(taskDto);
        return ResponseEntity.ok("Success");
    }

}
