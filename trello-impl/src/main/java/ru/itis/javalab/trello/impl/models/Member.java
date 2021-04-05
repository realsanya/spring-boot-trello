package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends AutoincrementEntity{

    //add Dashboards

    //member не нужен, у доски будет юзер айди и роль в конкретной доски
}
