package ru.itis.javalab.trello.api.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService <UserDto, Long> {

    public Optional<UserDto> getUserByEmail(String email);

    public Optional<UserDto> getUserById(Long userId);

    List<UserDto> getAllUsers();

    public void save(UserDto userDto);

    void signUpAfterOAuth(String email, String name, String surname, String provider);

    void updateUserAfterOAuth(UserDto userDto, String name, String toString);
}
