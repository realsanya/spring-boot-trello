package ru.itis.javalab.trello.impl.services;

import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.LoginForm;
import ru.itis.javalab.trello.api.services.LoginService;

@Service
public class LoginServiceImpl implements LoginService<LoginForm, Long> {
}
