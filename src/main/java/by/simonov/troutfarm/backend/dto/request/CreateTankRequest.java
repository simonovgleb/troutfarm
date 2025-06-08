package by.simonov.troutfarm.backend.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTankRequest(
        @NotBlank String name,
        @Positive int capacity,
        @DecimalMin("-100") @DecimalMax("100") BigDecimal temperature,
        @PositiveOrZero @Max(100) BigDecimal oxygenLevel,
        UUID batchId
) {
}
