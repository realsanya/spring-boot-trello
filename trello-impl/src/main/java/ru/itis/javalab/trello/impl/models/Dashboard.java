package ru.itis.javalab.trello.impl.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dashboard")
@NamedEntityGraph(
        name="Dashboard.Tasks",
        attributeNodes = @NamedAttributeNode(
                value = "tasks"
        )
)
public class Dashboard extends AutoincrementEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(value = EnumType.STRING)
    private ProjectRole projectRole;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

}
