package ru.itis.javalab.trello.web.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.trello.api.dto.DashboardDto;
import ru.itis.javalab.trello.api.dto.ProjectDto;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.api.services.DashboardService;


@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @ApiOperation(value = "Получение dasboards по id проекта")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найдены", response = DashboardDto.class)})
    @GetMapping("/dashboards")
    public ResponseEntity<Page<DashboardDto>> dashboards(@RequestParam Long projectId, Pageable pageable){
        return ResponseEntity.ok(dashboardService.getByProjectId(projectId, pageable));
    }

    @ApiOperation(value = "Получение dasboard по id проекта и id юзера")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Успешно найден", response = DashboardDto.class)})
    @GetMapping("/dashboard")
    public DashboardDto dashboard(@RequestParam Long projectId, @RequestParam Long userId) throws Throwable {
        return (DashboardDto) dashboardService.getByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("Dashboard not found"));
    }
}
