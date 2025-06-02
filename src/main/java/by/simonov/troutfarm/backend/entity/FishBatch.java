package by.simonov.troutfarm.backend.entity;

import by.simonov.troutfarm.backend.entity.type.BatchStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "fish_batch")
@Getter
@Setter
@NoArgsConstructor
public class FishBatch {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    @Column(name = "fish_type")
    private String fishType;

    @Column(name = "arrival_date")
    private OffsetDateTime arrivalDate;

    @Column(name = "initial_count")
    private int initialCount;

    @Column(name = "average_weight_grams")
    private BigDecimal averageWeightGrams;

    @Column(name = "current_count")
    private int currentCount;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;
}
