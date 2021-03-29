package ru.itis.javalab.trello.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.trello.impl.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
