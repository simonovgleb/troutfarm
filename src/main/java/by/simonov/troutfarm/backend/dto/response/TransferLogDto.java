package by.simonov.troutfarm.backend.dto.response;

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

    private TankDto fromTank;

    private TankDto toTank;

    private FishBatchDto batch;

    private int count;

    private OffsetDateTime dateTime;

    private UserDto operator;
}