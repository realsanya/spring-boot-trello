package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.services.DashboardService;
import ru.itis.javalab.trello.impl.repositories.DashboardRepository;

import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService<DashboardDto, Long> {

    private final DashboardRepository dashboardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DashboardServiceImpl(DashboardRepository dashboardRepository, ModelMapper modelMapper) {
        this.dashboardRepository = dashboardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<DashboardDto> getByProjectId(Long projectId, Pageable pageable) {
        return dashboardRepository.findByProjectId(projectId, pageable).map(
                dashboard -> modelMapper.map(dashboard, DashboardDto.class));
    }

    @Override
    public Optional<DashboardDto> getByProjectIdAndUserId(Long projectId, Long userId) {
        return dashboardRepository.findByProjectIdAndUserId(projectId, userId)
                .map(dashboard -> modelMapper.map(dashboard,DashboardDto.class));
    }
}
