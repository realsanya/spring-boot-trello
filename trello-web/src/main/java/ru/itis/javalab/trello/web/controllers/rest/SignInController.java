package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.javalab.trello.api.dto.*;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.web.security.jwt.provider.JwtAuthenticationProvider;

import java.util.Optional;

@Controller
public class SignInController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserService userService;

    public SignInController(JwtAuthenticationProvider jwtAuthenticationProvider, UserService userService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userService = userService;
    }

    @RequestMapping("/signIn")
    public ResponseEntity<?> handleSignIn(@RequestBody SignInForm signInForm) throws Throwable {
        System.out.println(signInForm);
        String token = jwtAuthenticationProvider.createToken(signInForm.getEmail());
        jwtAuthenticationProvider.authenticate(token);
        UserDto userDto = (UserDto) userService.getUserByEmail(signInForm.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        SignInDto signInDto = SignInDto.builder()
                .token(token)
                .userData(userDto)
                .build();
        return ResponseEntity.ok(signInDto);
    }
}
