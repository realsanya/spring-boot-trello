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
    private Date dateOfBirth;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Project> projectList;
    @OneToMany
    private List<Comment> comments;

    public enum State {
        CONFIRMED("CONFIRMED"),
        NOT_CONFIRMED("NOT_CONFIRMED"),
        ACTIVE("ACTIVE"),
        BANNED("BANNED");

        private final String state;

        State(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    public enum Role {
        ADMIN,
        USER,
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
