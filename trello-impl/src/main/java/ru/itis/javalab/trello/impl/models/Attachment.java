package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attachment")
public class Attachment extends AutoincrementEntity {
    @ManyToOne
    @JoinColumn(name = "task_id")
    public Task task;
    private String type;
    private String path;
}
