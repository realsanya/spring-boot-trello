package ru.itis.javalab.trello.impl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class User extends AutoincrementEntity{
    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private State state;
    private Date dateOfBirth;

    public enum State {
        CONFIRMED, NOT_CONFIRMED, BANNED
    }

    public Boolean isBanned() {
        return this.state == State.BANNED;
    }
}
