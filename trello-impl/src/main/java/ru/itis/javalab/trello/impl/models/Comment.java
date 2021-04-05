package ru.itis.javalab.trello.impl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends AutoincrementEntity{
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne
    private Task task;

    public String text;
    @CreatedDate
    public Date createdDate;
}
