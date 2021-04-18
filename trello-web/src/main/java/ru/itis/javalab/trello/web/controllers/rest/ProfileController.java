package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public String getHello(){
        return "Hello World!";
    }
}
