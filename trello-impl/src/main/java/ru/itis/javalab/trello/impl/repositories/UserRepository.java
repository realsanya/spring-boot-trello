package ru.itis.javalab.trello.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
