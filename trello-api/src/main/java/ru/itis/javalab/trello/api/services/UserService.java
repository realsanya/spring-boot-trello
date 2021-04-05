package ru.itis.javalab.trello.api.services;

public interface UserService <UserDto, Long> {
    public void signUp(UserDto user);
}
