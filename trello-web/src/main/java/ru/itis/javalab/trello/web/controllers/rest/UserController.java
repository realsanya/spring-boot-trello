package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.web.exception.PageNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello World!";
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) throws Throwable {
        return (UserDto) userService.getUserById(id)
                .orElseThrow(() -> new PageNotFoundException("Page not found"));
    }
}
