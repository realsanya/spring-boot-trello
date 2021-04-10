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
public class Task extends AutoincrementEntity{
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;
    @OneToMany
    private List<Comment> comments;
    @OneToMany
    private List<Attachment> attachments;
    @OneToMany
    private List<CheckItem> checkItems;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfStart;
    private Date dateOfEnd;
}
