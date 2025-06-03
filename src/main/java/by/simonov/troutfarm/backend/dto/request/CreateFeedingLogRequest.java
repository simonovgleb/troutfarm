package by.simonov.troutfarm.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateFeedingLogRequest(
        @NotNull UUID batchId,
        @NotNull OffsetDateTime dateTime,
        @NotBlank String foodType,
        @Positive BigDecimal foodAmountGrams,
        @NotNull UUID operatorId
) {

}
