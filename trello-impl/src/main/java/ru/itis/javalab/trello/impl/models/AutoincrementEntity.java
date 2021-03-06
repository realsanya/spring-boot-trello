package ru.itis.javalab.trello.impl.models;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class AutoincrementEntity {
    @Id
    @GeneratedValue
    private Long id;
}
