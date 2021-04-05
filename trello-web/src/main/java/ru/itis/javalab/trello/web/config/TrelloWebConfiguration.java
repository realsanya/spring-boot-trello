package ru.itis.javalab.trello.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.itis.javalab.trello.impl.config.TrelloImplConfiguration;

@Configuration
@Import({TrelloImplConfiguration.class})
public class TrelloWebConfiguration {
}
