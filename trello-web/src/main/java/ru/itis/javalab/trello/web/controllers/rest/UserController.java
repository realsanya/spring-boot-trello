package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.aspect.CachingAspect;
import ru.itis.javalab.trello.web.exception.PageNotFoundException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "Получение пользователя по id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найден", response = UserDto.class)})
    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id) throws Throwable {
        return (UserDto) userService.getUserById(id)
                .orElseThrow(() -> new PageNotFoundException("Page not found"));
    }
}
