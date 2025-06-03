package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.type.BatchStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FishBatchDto {

    private UUID id;

    private String name;

    private String fishType;

    private OffsetDateTime arrivalDate;

    private int initialCount;

    private BigDecimal averageWeightGrams;

    private int currentCount;

    private BatchStatus status;
}

