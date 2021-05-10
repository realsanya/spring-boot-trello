package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.DashboardService;
import ru.itis.javalab.trello.api.services.ProjectService;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.models.Dashboard;
import ru.itis.javalab.trello.impl.models.Project;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.impl.repositories.DashboardRepository;
import ru.itis.javalab.trello.impl.repositories.ProjectRepository;

import java.util.Date;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements ProjectService<ProjectDto, Long> {

    private final ProjectRepository projectRepository;
    private final DashboardService dashboardService;
    private final DashboardRepository dashboardRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, DashboardService dashboardService, DashboardRepository dashboardRepository, UserService userService, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.dashboardService = dashboardService;
        this.dashboardRepository = dashboardRepository;
        this.userService = userService;
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
    public void addMember(Long projectId, String memberEmail) {
        System.out.println(projectId + " " + memberEmail);
        UserDto userDto = modelMapper.map(userService.getUserByEmail(memberEmail).orElse(null), UserDto.class);
        DashboardDto dashboardDto = DashboardDto.builder()
                .projectId(projectId)
                .user(userDto)
                .projectRole(userDto.getRole())
                .build();
        dashboardRepository.save(modelMapper.map(dashboardDto, Dashboard.class));
    }

    @Override
    public void createProject(ProjectDto projectDto) {
        projectDto.setId(null);
        projectDto.setDateOfStart(new Date());
        projectRepository.save(modelMapper.map(projectDto, Project.class));
        dashboardService.createDashboard(projectDto.getUserId(), projectDto.getUserId());
    }

    @Override
    public void deleteProject(ProjectDto projectDto) {
        projectRepository.delete(modelMapper.map(projectDto, Project.class));
    }
}
