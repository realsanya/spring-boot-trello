package ru.itis.javalab.trello.api.services;

public interface SignUpService<SignUpForm, Long> {
    void signUp(SignUpForm form);
    boolean userIsExist(String email);
}
