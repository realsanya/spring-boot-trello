package ru.itis.javalab.trello.impl.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.*;
import ru.itis.javalab.trello.api.services.SignInService;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.models.Token;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.impl.repositories.TokenRepository;
import ru.itis.javalab.trello.impl.repositories.UserRepository;
import ru.itis.javalab.trello.impl.utlis.TokenGenerator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class SignInServiceImpl implements SignInService<SignInDto, Long> {

    @Autowired
    private UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    private TokenGenerator tokenGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Algorithm algorithm;

    @SneakyThrows
    @Override
    public TokenDto signIn(SignInForm emailPassword) {
        User user = userRepository.findUserByEmail(emailPassword.getEmail())
                .orElseThrow((Supplier<Throwable>) () -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(emailPassword.getPassword(), user.getPassword())) {
            String token = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("state", user.getState().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("createdAt", LocalDateTime.now().toString())
                    .sign(algorithm);

            Token t = Token.builder()
                    .token(token)
                    .user(user)
                    .build();
            tokenRepository.save(t);
            return TokenDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    @Override
    public TokenDto googleSignIn(GoogleForm googleForm) {
        Optional<User> foundUser = userRepository.findUserByEmail(googleForm.getEmail());
        if(!foundUser.isPresent()) {
            User user = User.builder()
                    .email(googleForm.getEmail())
                    .auth_provider(User.AuthProvider.GOOGLE)
                    .name(googleForm.getName())
                    .surname(googleForm.getSurname())
                    .build();
            userRepository.save(user);
        }
        return null;
    }
}
