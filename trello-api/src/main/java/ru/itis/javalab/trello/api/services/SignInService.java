package ru.itis.javalab.trello.api.services;

import ru.itis.javalab.trello.api.dto.SignInForm;
import ru.itis.javalab.trello.api.dto.TokenDto;

public interface SignInService<SignInDto, Long> {
      TokenDto signIn(SignInForm emailPassword);
}
