package ru.itis.javalab.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SignInForm {
    private String email;
    private String password;
}
