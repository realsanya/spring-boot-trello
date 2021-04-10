package ru.itis.javalab.trello.api.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService <UserDto, Long> {

    public UserDto getUserByEmail(String email);

    public Optional<UserDto> getUserById(Long userId);

    List<UserDto> getAllUsers();

    public void save(UserDto userDto);
}
