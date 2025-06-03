package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.entity.Tank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TransferLogDto {

    private UUID id;

    private Tank fromTank;

    private Tank toTank;

    private FishBatch batch;

    private int count;

    private OffsetDateTime dateTime;
}