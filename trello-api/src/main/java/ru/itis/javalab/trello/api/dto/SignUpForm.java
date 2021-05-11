package ru.itis.javalab.trello.api.dto;

import lombok.Data;
import ru.itis.javalab.trello.api.validation.ValidNames;
import ru.itis.javalab.trello.api.validation.ValidPassword;
import ru.itis.javalab.trello.api.validation.ValidPasswords;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ValidNames(
        message = "Имя и фамилия совпадают",
        name = "name",
        surname = "surname"
)
@ValidPasswords(
        message = "Пароли не совпадают",
        password = "password",
        passwordAgain = "confirmPassword"
)
public class SignUpForm {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    @Size(min = 8)
    private String email;
    private Date dateOfBirth;
    @ValidPassword(message = "Плохой пароль")
    private String password;
    @NotBlank
    private String confirmPassword;
}
