package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.trello.api.dto.CheckItemDto;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.dto.TaskDto;
import ru.itis.javalab.trello.api.services.CheckItemService;

@RestController
public class CheckItemController {

    private final CheckItemService checkItemService;

    @Autowired
    public CheckItemController(CheckItemService checkItemService) {
        this.checkItemService = checkItemService;
    }

    @ApiOperation(value = "Получение чеклиста по id задачи")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найден", response = CheckItemDto.class)})
    @RequestMapping("/check/all")
    public Page<CheckItemDto> findAllByTaskID(@RequestParam Long taskId, Pageable pageable){
        return checkItemService.getAllByTaskId(taskId, pageable);
    }


}
