package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.FishBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FishBatchRepository extends JpaRepository<FishBatch, UUID> {
}
