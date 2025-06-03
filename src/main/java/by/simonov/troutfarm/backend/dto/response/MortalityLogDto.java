package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.entity.type.DeathReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MortalityLogDto {

    private UUID id;

    private FishBatch batch;

    private OffsetDateTime dateTime;

    private int count;

    private DeathReason reason;
}
