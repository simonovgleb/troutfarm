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

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tank")
@Getter
@Setter
@NoArgsConstructor
public class Tank {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    private int capacity;

    private BigDecimal temperature;

    @Column(name = "oxygen_level")
    private BigDecimal oxygenLevel;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private FishBatch batch;
}
