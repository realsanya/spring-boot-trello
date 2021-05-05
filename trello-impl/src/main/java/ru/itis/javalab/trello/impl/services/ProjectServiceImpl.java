package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.services.ProjectService;
import ru.itis.javalab.trello.impl.models.Project;
import ru.itis.javalab.trello.impl.repositories.ProjectRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements ProjectService<ProjectDto, Long> {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ProjectDto> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(project -> modelMapper.map(project, ProjectDto.class));
    }

    @Override
    public Page<ProjectDto> getAllProjectsByUserId(Long userId, Pageable pageable) {
        return projectRepository.findAllByUserId(userId, pageable).map(
                project -> modelMapper.map(project, ProjectDto.class));
    }

    @Override
    public Optional<ProjectDto> getById(Long id) {
        return projectRepository.findById(id)
                .map(project -> modelMapper.map(project, ProjectDto.class));
    }

    @Override
    public void createProject(ProjectDto projectDto) {
        System.out.println(projectDto);
        projectDto.setId(null);
        projectDto.setDateOfStart(new Date());
        projectRepository.save(modelMapper.map(projectDto, Project.class));
    }
}
