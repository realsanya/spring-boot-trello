package ru.itis.javalab.trello.api.service;

public interface UserService <UserDto, Long> {
    public void signUp(UserDto user);
}
