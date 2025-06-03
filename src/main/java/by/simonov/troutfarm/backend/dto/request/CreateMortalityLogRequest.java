package by.simonov.troutfarm.backend.dto.request;

import by.simonov.troutfarm.backend.entity.type.DeathReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateMortalityLogRequest(
        @NotNull UUID batchId,
        @NotNull OffsetDateTime dateTime,
        @Positive int count,
        @NotNull DeathReason reason,
        @NotNull UUID operatorId
) {
}
