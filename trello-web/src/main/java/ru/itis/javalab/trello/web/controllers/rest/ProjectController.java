package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.api.services.ProjectService;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Получение всех проектов")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = ProjectDto.class)})
    @GetMapping("/proj")
    public Page<ProjectDto> findAll(Pageable pageable){
        return projectService.getAll(pageable);
    }

    @ApiOperation(value = "Получение проектов по id пользователя")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = ProjectDto.class)})
    @GetMapping("/projects")
    public Page<ProjectDto> findAllByUserID(@RequestParam Long userId, Pageable pageable){
        return projectService.getAllProjectsByUserId(userId, pageable);
    }

    @ApiOperation(value = "Получение проекта по id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = ProjectDto.class)})
    @GetMapping("/project/{id}")
    public ProjectDto findProjectById(@PathVariable Long id) throws Throwable {
        System.out.println(id);
        return (ProjectDto) projectService.getById(id)
                .orElseThrow(() -> new NotFoundException("Project by id not found"));
    }

    @ApiOperation(value = "Создание нового проекта")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно сохранен", response = ProjectDto.class)})
    @PostMapping("/project/add")
    public ResponseEntity<?> addProject(@RequestBody ProjectDto projectDto){
        System.out.println(projectDto);
        projectService.createProject(projectDto);
        return ResponseEntity.ok("Success");
    }

    // TODO
    @ApiOperation(value = "Добавление нового пользователя в проект " +
            "(происходит создание доски пользователя, привязанной к этому проекту) ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно сохранен", response = ProjectDto.class)})
    @PostMapping("/project/member/add")
    public ResponseEntity<?> addMemberToProject(@RequestBody ProjectDto projectDto){
        System.out.println(projectDto);
        projectService.createProject(projectDto);
        return ResponseEntity.ok("Success");
    }
}
