package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateTaskRequest;
import by.simonov.troutfarm.backend.dto.response.TaskDto;
import by.simonov.troutfarm.backend.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<TaskDto> findAll();

    TaskDto findById(UUID id);

    Task save(Task task);

    UUID create(CreateTaskRequest request);

    void delete(UUID id);

    void update(UUID id, CreateTaskRequest request);
}
