package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateTaskRequest;
import by.simonov.troutfarm.backend.dto.response.TaskDto;
import by.simonov.troutfarm.backend.entity.Task;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.entity.type.Role;
import by.simonov.troutfarm.backend.entity.type.TaskStatus;
import by.simonov.troutfarm.backend.repository.TaskRepository;
import by.simonov.troutfarm.backend.service.TaskService;
import by.simonov.troutfarm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final EntityMapper mapper;
    private final UserService userService;

    @Override
    public List<TaskDto> findAll(UserPrincipal principal) {
        List<Task> tasks;
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN))) {
            tasks = repository.findAll();
        } else {
            tasks = repository.findAllByAssignedToId(principal.getId());
        }
        return tasks.stream().map(mapper::toDto).toList();
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
    public UUID create(CreateTaskRequest request, UUID createdById) {
        Task task = mapper.toEntity(request);

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedBy(userService.findById(createdById));
        if (request.assignedToId() != null) {
            task.setAssignedTo(userService.findById(request.assignedToId()));
        }

        return save(task).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateTaskRequest request) {
        Task entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        if (request.assignedToId() != null) {
            if ((entity.getAssignedTo() == null) ||
                    (!entity.getAssignedTo().getId().equals(request.assignedToId()))) {
                entity.setAssignedTo(userService.findById(request.assignedToId()));
            }
        } else {
            entity.setAssignedTo(null);
        }

        save(entity);
    }

    @Override
    public void completeTask(UUID id) {
        Task entity = repository.findById(id).orElseThrow();
        entity.setStatus(TaskStatus.COMPLETED);
        save(entity);
    }
}
