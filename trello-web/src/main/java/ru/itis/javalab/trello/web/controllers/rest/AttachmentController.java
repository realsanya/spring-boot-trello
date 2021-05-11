package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.AttachmentDto;
import ru.itis.javalab.trello.api.services.AttachmentService;

@RestController
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation(value = "Получение вложений по id задачи")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = AttachmentDto.class)})
    @GetMapping("/attachments")
    public Page<AttachmentDto> findAllByTaskID(@RequestParam Long taskId, Pageable pageable){
        System.out.println(taskId);
        return attachmentService.getByTaskId(taskId, pageable);
    }

    @ApiOperation(value = "Добавление нового вложения")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно сохранен", response = AttachmentDto.class)})
    @PostMapping("/uploadfile")
    public ResponseEntity<?> addAttachment(@RequestBody AttachmentDto attachmentDto){
        System.out.println(attachmentDto);
        attachmentService.addAttachment(attachmentDto);
        return ResponseEntity.ok("Success");
    }
}
