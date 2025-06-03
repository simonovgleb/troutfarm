package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateTaskRequest;
import by.simonov.troutfarm.backend.dto.response.TaskDto;
import by.simonov.troutfarm.backend.entity.Task;
import by.simonov.troutfarm.backend.repository.TaskRepository;
import by.simonov.troutfarm.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<TaskDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public TaskDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public UUID create(CreateTaskRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateTaskRequest request) {
        Task entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
