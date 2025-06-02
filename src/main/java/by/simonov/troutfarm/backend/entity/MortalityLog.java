package by.simonov.troutfarm.backend.entity;

import by.simonov.troutfarm.backend.entity.type.DeathReason;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "mortality_log")
@Getter
@Setter
@NoArgsConstructor
public class MortalityLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private FishBatch batch;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    private int count;

    @Enumerated(EnumType.STRING)
    private DeathReason reason;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private User operator;
}
