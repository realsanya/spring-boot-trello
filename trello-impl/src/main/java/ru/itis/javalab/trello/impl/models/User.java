package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class User extends AutoincrementEntity{
    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private AuthProvider auth_provider;
    private Date dateOfBirth;
    @OneToMany
    private List<Project> projects;
    @OneToMany
    private List<Comment> comments;


    public enum State {
        ACTIVE,
        BANNED,
    }

    public enum Role {
        ADMIN,
        USER,
    }

    public enum AuthProvider {
        LOCAL, GOOGLE
    }

    public boolean isActive(){
        return  this.state == State.ACTIVE;
    }

    public boolean isBanned(){
        return  this.state == State.BANNED;
    }

    public boolean isAdmin(){
        return this.role == Role.ADMIN;
    }
}
