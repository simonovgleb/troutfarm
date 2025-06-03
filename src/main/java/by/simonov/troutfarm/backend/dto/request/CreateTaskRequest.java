package by.simonov.troutfarm.backend.dto.request;

import by.simonov.troutfarm.backend.entity.type.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank String title,
        String description,
        @NotNull UUID assignedToId,
        @NotNull UUID createdById,
        @NotNull OffsetDateTime dueDate,
        @NotNull TaskStatus status
) {
}
