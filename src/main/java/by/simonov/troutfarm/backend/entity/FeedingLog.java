package by.simonov.troutfarm.backend.entity;

import by.simonov.troutfarm.backend.entity.type.FoodType;
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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "feeding_log")
@Getter
@Setter
@NoArgsConstructor
public class FeedingLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private FishBatch batch;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type")
    private FoodType foodType;

    @Column(name = "food_amount_grams")
    private BigDecimal foodAmountGrams;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private User operator;
}
