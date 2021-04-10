package ru.itis.javalab.trello.api.dto;

import lombok.Data;

@Data
public class SignInForm {
    private String email;
    private String password;
}
