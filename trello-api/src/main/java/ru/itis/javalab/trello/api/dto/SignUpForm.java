package ru.itis.javalab.trello.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    @Size(min = 8)
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    private String password;
    @NotBlank
    private String confirmPassword;
}
