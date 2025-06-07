package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.entity.type.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FeedingLogDto {

    private UUID id;

    private FishBatch batch;

    private OffsetDateTime dateTime;

    private FoodType foodType;

    private BigDecimal foodAmountGrams;

    private UserDto operator;
}

