package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.AttachmentDto;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.services.AttachmentService;
import ru.itis.javalab.trello.impl.models.Attachment;
import ru.itis.javalab.trello.impl.repositories.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService<AttachmentDto, Long> {

    private final AttachmentRepository attachmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, ModelMapper modelMapper) {
        this.attachmentRepository = attachmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AttachmentDto> getByTaskId(Long taskId, Pageable pageable) {
        return attachmentRepository.findByTaskId(taskId, pageable).map(
                attachment -> modelMapper.map(attachment, AttachmentDto.class));
    }

    @Override
    public void addAttachment(AttachmentDto attachmentDto) {
        attachmentRepository.save(modelMapper.map(attachmentDto, Attachment.class));
    }
}
