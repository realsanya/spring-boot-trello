package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping
    public void updateUserData(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }
}
