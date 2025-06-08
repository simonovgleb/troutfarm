package by.simonov.troutfarm.backend.dto.request;

import by.simonov.troutfarm.backend.entity.type.BatchStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateFishBatchRequest(
        @NotBlank String name,
        @NotBlank String fishType,
        @NotNull OffsetDateTime arrivalDate,
        @Positive int initialCount,
        @Positive @DecimalMax("999.99") BigDecimal averageWeightGrams,
        @PositiveOrZero int currentCount,
        @NotNull BatchStatus status
) {
}
