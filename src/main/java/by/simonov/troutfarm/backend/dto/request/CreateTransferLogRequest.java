package by.simonov.troutfarm.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransferLogRequest(
        @NotNull UUID fromTankId,
        @NotNull UUID toTankId,
        @NotNull UUID batchId,
        @Positive int count,
        @NotNull OffsetDateTime dateTime,
        @NotNull UUID operatorId
) {
}
