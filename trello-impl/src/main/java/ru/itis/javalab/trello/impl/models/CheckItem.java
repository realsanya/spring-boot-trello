package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "check_item")
public class CheckItem extends AutoincrementEntity {
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private String text;
    private String status;
}


