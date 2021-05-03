package ru.itis.javalab.trello.impl.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"ru.itis.javalab.trello.impl.services", "ru.itis.javalab.trello.impl.aspect"})
@EnableJpaRepositories(basePackages = "ru.itis.javalab.trello.impl.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "ru.itis.javalab.trello.impl.models")
@EnableAspectJAutoProxy
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
