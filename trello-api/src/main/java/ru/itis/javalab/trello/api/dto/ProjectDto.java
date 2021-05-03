package ru.itis.javalab.trello.api.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Date dateOfStart;
    private UserDto userDto;
    private List<DashboardDto> dashboards;
}
