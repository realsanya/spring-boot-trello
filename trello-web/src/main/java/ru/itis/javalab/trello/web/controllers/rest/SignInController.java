package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.SignInForm;
import ru.itis.javalab.trello.api.dto.SignUpForm;
import ru.itis.javalab.trello.api.dto.SuccessDto;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.web.security.provider.JwtAuthenticationProvider;


@RestController
public class SignInController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public SignInController(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @RequestMapping("/signIn")
    public ResponseEntity<?> handleSignUp(@RequestBody SignUpForm signUpForm) throws NotFoundException {
        System.out.println(signUpForm);
        String token = jwtAuthenticationProvider.createToken(signUpForm.getEmail());
        jwtAuthenticationProvider.authenticate(token);
        return ResponseEntity.ok(SuccessDto.builder()
                .message("You successfully auth!" + token)
                .build());
    }


}
