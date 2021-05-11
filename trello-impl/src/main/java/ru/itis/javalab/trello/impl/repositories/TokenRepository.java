package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.Token;
import ru.itis.javalab.trello.impl.models.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, User> {
    Optional<Token> findByToken(String token);
}
