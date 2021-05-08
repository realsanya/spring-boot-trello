package ru.itis.javalab.trello.impl;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrelloImplApplication{

    @Value("${jwt.token.secret}")
    private String secret;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public static void main(String[] args) {
        SpringApplication.run(TrelloImplApplication.class, args);
    }

}
