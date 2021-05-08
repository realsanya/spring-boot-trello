package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.itis.javalab.trello.api.dto.SignUpForm;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.SignUpService;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.web.events.OnRegisterSuccessEvent;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    //TODO убрать отсюда сервис
    private final UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public SignUpController(SignUpService signUpService, UserService userService) {
        this.signUpService = signUpService;
        this.userService = userService;
    }

    @ApiOperation(value = "Регистрация пользователя")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно добавлен", response = SignUpForm.class)})
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> handleSignUp(@Valid @RequestBody SignUpForm signUpForm, WebRequest webRequest) {
        if (signUpService.userIsExist(signUpForm.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Пользователь с таким email уже существует"));
            return ResponseEntity.badRequest().body(signUpForm);
        }
        signUpService.signUp(signUpForm);
        UserDto userDto =  (UserDto) userService.getUserByEmail(signUpForm.getEmail()).orElse(null);
        String appUrl = webRequest.getContextPath();
        eventPublisher.publishEvent(new OnRegisterSuccessEvent(userDto, appUrl));
//        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован!"));
        return ResponseEntity.ok(signUpForm);
    }
}
