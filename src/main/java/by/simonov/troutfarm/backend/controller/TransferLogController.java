package by.simonov.troutfarm.backend.controller;

import by.simonov.troutfarm.backend.dto.request.CreateTransferLogRequest;
import by.simonov.troutfarm.backend.dto.response.TransferLogDto;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.service.TransferLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferLogController {

    private final TransferLogService service;

    public TransferLogController(TransferLogService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_OPERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UUID> create(@Valid @RequestBody CreateTransferLogRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<TransferLogDto>> getAll(Authentication authentication) {
        return ResponseEntity.ok(service.findAll((UserPrincipal) authentication.getPrincipal()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferLogDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid CreateTransferLogRequest request) {
        service.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}