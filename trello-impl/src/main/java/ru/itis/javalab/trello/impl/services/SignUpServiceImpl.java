package ru.itis.javalab.trello.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.SignUpForm;
import ru.itis.javalab.trello.api.services.SignUpService;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.impl.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService<SignUpForm, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //TODO прикрутить отправку email

    @Override
    public void signUp(SignUpForm form) {
        if(form.getPassword().equals(form.getConfirmPassword())) {
            Optional<User> userIsExist = userRepository.findUserByEmail(form.getEmail());
            if(!userIsExist.isPresent()) {
                User user = User.builder()
                        .name(form.getName())
                        .surname(form.getSurname())
                        .email(form.getEmail())
                        .password(passwordEncoder.encode(form.getPassword()))
                        .role(User.Role.USER)
                        .state(User.State.NOT_CONFIRMED)
                        .build();
                userRepository.save(user);
            } else throw new IllegalArgumentException("Пользователь с таким email уже существует!");
        } else throw new IllegalArgumentException("Пароли не совпадают!");
    }
}
