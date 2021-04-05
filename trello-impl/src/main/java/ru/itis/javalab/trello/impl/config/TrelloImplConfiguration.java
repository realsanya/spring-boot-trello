package ru.itis.javalab.trello.impl.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@ComponentScan("ru.itis.javalab.trello.impl.repository")
@ComponentScan("ru.itis.javalab.trello.impl.services")
@EnableJpaRepositories(basePackages = "ru.itis.javalab.trello.impl.repositories")
@EntityScan(basePackages = "ru.itis.javalab.trello.impl.entity")
public class TrelloImplConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
