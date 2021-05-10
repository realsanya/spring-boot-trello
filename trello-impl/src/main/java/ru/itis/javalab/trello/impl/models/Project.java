package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@NamedEntityGraph(
        name="Project.Dashboards",
        attributeNodes = @NamedAttributeNode(
                value = "dashboards"
        )
)
public class Project extends AutoincrementEntity {
    private String name;
    private String description;
    private Date dateOfStart;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dashboard> dashboards;
}
