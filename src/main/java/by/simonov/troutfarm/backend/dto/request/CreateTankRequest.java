package by.simonov.troutfarm.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTankRequest(
        @NotBlank String name,
        @Positive int capacity,
        @Positive BigDecimal temperature,
        @Positive BigDecimal oxygenLevel,
        UUID batchId
) {
}
