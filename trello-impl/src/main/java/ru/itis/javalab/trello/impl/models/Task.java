package ru.itis.javalab.trello.impl.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task extends AutoincrementEntity {
    private String name;
    private String description;
    @ManyToOne
    private Dashboard dashboard;

    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<File> files;
}
