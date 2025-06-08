package by.simonov.troutfarm.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TankDto {

    private UUID id;

    private String name;

    private int capacity;

    private BigDecimal temperature;

    private BigDecimal oxygenLevel;

    private FishBatchDto batch;
}
