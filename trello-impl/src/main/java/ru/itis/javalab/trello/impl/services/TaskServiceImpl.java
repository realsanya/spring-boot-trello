package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.CommentDto;
import ru.itis.javalab.trello.api.dto.TaskData;
import ru.itis.javalab.trello.api.dto.TaskDto;
import ru.itis.javalab.trello.api.services.CommentService;
import ru.itis.javalab.trello.api.services.TaskService;
import ru.itis.javalab.trello.impl.models.Task;
import ru.itis.javalab.trello.impl.repositories.CommentRepository;
import ru.itis.javalab.trello.impl.repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService<TaskDto, Long> {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<TaskDto> getByDashboardId(Long dashboardId, Pageable pageable) {
        return taskRepository.findByDashboardId(dashboardId, pageable).map(
                task -> modelMapper.map(task, TaskDto.class));
    }

    @Override
    public Optional<TaskDto> getById(Long id) {
        return taskRepository.findById(id)
                .map(task -> modelMapper.map(task, TaskDto.class));
    }

    @Override
    public void addTask(TaskDto taskDto) {
        taskDto.setId(null);
        taskDto.setStatus("TODO");
        taskRepository.save(modelMapper.map(taskDto, Task.class));
    }

    @Override
    public void archiveTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDto.setStatus("ARCHIVED");
            taskRepository.save(modelMapper.map(taskDto, Task.class));
        }

    }
}
