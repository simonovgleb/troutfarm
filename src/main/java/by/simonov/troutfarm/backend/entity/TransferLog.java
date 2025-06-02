package by.simonov.troutfarm.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "transfer_log")
@Getter
@Setter
@NoArgsConstructor
public class TransferLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_tank_id")
    private Tank fromTank;

    @ManyToOne
    @JoinColumn(name = "to_tank_id")
    private Tank toTank;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private FishBatch batch;

    private int count;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private User operator;
}
