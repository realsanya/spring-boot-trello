package ru.itis.javalab.trello.impl.entity;

import javax.persistence.ManyToOne;

public class Project extends AutoincrementEntity {
    private String name;
    private String description;
    // TODO: подумать над тем как определять роль пользователя только в зависимости от проекта

}
