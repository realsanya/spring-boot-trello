package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.itis.javalab.trello.api.dto.SignUpForm;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.SignUpService;


import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @ApiOperation(value = "Регистрация пользователя")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно добавлен", response = SignUpForm.class)})
    @PostMapping(value = "/signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleSignUp(@Valid @RequestBody SignUpForm signUpForm) {
        if (signUpService.userIsExist(signUpForm.getEmail())) {
            return ResponseEntity.badRequest().body(signUpForm);
        }
        signUpService.signUp(signUpForm);
        return ResponseEntity.ok(signUpForm);
    }
}
