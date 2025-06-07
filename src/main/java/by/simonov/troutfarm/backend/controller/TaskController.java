package by.simonov.troutfarm.backend.controller;

import by.simonov.troutfarm.backend.dto.request.CreateTaskRequest;
import by.simonov.troutfarm.backend.dto.response.TaskDto;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@PreAuthorize("isAuthenticated()")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UUID> create(@Valid @RequestBody CreateTaskRequest request, Authentication authentication) {
        return ResponseEntity.ok(service.create(request, ((UserPrincipal) authentication.getPrincipal()).getId()));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll(Authentication authentication) {
        return ResponseEntity.ok(service.findAll((UserPrincipal) authentication.getPrincipal()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid CreateTaskRequest request) {
        service.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    public ResponseEntity<Void> completeTask(@PathVariable UUID id) {
        service.completeTask(id);
        return ResponseEntity.noContent().build();
    }
}
