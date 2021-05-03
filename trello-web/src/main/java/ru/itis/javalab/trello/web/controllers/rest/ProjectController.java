package ru.itis.javalab.trello.web.controllers.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.api.services.ProjectService;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/proj")
    public Page<ProjectDto> findAll(Pageable pageable){
        return projectService.getAll(pageable);
    }

    @GetMapping("/projects")
    public Page<ProjectDto> findAllByUserID(@RequestParam(required = false) Long userId, Pageable pageable){
        return projectService.getAllProjectsByUserId(userId, pageable);
    }

    @GetMapping("/project/{id}")
    public ProjectDto project(@PathVariable Long id) throws Throwable {
        System.out.println(id);
        return (ProjectDto) projectService.getById(id)
                .orElseThrow(() -> new NotFoundException("Project by id not found"));
    }
}
