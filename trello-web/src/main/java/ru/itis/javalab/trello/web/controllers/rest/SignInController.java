package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.javalab.trello.api.dto.*;
import ru.itis.javalab.trello.api.services.SignInService;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.web.security.jwt.provider.JwtAuthenticationProvider;

import java.util.Optional;

@Controller
public class SignInController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserService userService;
    private final SignInService signInService; //TODO убрать отсюда


    public SignInController(JwtAuthenticationProvider jwtAuthenticationProvider, UserService userService, SignInService signInService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userService = userService;
        this.signInService = signInService;
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

    @RequestMapping("/signIn/google")
    public ResponseEntity<?> handleSignIn(@RequestBody GoogleForm googleForm) throws Throwable {
//        System.out.println(googleForm);
        Optional<UserDto> userDto = userService.getUserByEmail(googleForm.getEmail());
        if (!userDto.isPresent()) {
            UserDto userDto1 = signInService.googleSignIn(googleForm);
            String token = jwtAuthenticationProvider.createToken(googleForm.getEmail());
            jwtAuthenticationProvider.authenticate(token);
            SignInDto signInDto = SignInDto.builder()
                    .token(token)
                    .userData(userDto.orElse(null))
                    .build();
            return ResponseEntity.ok(signInDto);
        } else {
            String token = jwtAuthenticationProvider.createToken(googleForm.getEmail());
            jwtAuthenticationProvider.authenticate(token);
            SignInDto signInDto = SignInDto.builder()
                    .token(token)
                    .userData(userDto.orElse(null))
                    .build();
            return ResponseEntity.ok(signInDto);
        }
    }
}
