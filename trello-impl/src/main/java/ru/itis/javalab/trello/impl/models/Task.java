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
@Table(name = "task")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Task.Comments",
                attributeNodes = {@NamedAttributeNode("comments")}),
        @NamedEntityGraph(name = "Task.Attachments",
                attributeNodes = {@NamedAttributeNode("attachments")}),
        @NamedEntityGraph(name = "Task.CheckItems",
                attributeNodes = {@NamedAttributeNode("checkItems")}),
})
public class Task extends AutoincrementEntity{
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Attachment> attachments;
    @OneToMany(fetch = FetchType.LAZY)
    private List<CheckItem> checkItems;
    private TaskStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfStart;
    private Date dateOfEnd;

    public enum TaskStatus {
        TODO,
        PAUSE,
        ACTIVE,
        COMPLETED,
        ARCHIVED,
    }
}
