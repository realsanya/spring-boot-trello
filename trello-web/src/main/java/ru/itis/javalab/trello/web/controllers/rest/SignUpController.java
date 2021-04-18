package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.SignUpForm;
import ru.itis.javalab.trello.api.dto.SuccessDto;
import ru.itis.javalab.trello.api.services.SignUpService;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @RequestMapping("/signUp")
    public ResponseEntity<?> handleSignUp(@RequestBody SignUpForm signUpForm) {
        System.out.println(signUpForm);
        signUpService.signUp(signUpForm);
        return ResponseEntity.ok(SuccessDto.builder()
                .message("You successfully registered! Check your email box to confirm your account")
                .build());
    }
}
