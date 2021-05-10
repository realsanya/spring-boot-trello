package ru.itis.javalab.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {
    private TaskDto taskInfo;
    private Page<CommentDto> comments;
    private Page<AttachmentDto> attachments;
    private List<String> members;
}
